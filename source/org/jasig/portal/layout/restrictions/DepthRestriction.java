/**
 * Copyright � 2002 The JA-SIG Collaborative.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the JA-SIG Collaborative
 *    (http://www.jasig.org/)."
 *
 * THIS SOFTWARE IS PROVIDED BY THE JA-SIG COLLABORATIVE "AS IS" AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE JA-SIG COLLABORATIVE OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.jasig.portal.layout.restrictions;


import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.jasig.portal.PortalException;
import org.jasig.portal.utils.CommonUtils;

/**
 * DepthRestriction checks the priority restriction for a given UserLayoutNode object.
 * <p>
 * Company: Instructional Media &amp; Magic
 * 
 * @author <a href="mailto:mvi@immagic.com">Michael Ivanov</a>
 * @version $Revision$
 */

public class DepthRestriction extends UserLayoutRestriction {


         private Integer[] minDepthArray, maxDepthArray, depthArray;

         public DepthRestriction(String nodePath) {
            super(nodePath);
         }

         public DepthRestriction() {
            super();
         }

         /**
           * Returns the type of the current restriction
           * @return a restriction type respresented in the <code>RestrictionTypes</code> interface
           * @exception PortalException
           */
          public int getRestrictionType() {
           return RestrictionTypes.DEPTH_RESTRICTION|super.getRestrictionType();
          }

          /**
            * Parses the restriction expression of the current node
            * @exception PortalException
            */
         protected void parseRestrictionExpression() throws PortalException {
          try {
            String restrictionExp = getRestrictionExpression();
            List minDepthList = new Vector();
            List maxDepthList = new Vector();
            List depthList = new Vector();
            StringTokenizer st = new StringTokenizer(restrictionExp,",");
            while (st.hasMoreTokens()) {
             String token = st.nextToken();
             int index = token.indexOf('-');
             if ( index > 0 ) {
                 minDepthList.add(token.substring(0,index));
                 maxDepthList.add(token.substring(index+1));
             } else
                 depthList.add(token);
            }
               int size = minDepthList.size();
               minDepthArray = new Integer[size];
               maxDepthArray = new Integer[size];
               for ( int i = 0; i < size; i++ ) {
                 minDepthArray[i] = Integer.valueOf((String)minDepthList.get(i));
                 maxDepthArray[i] = Integer.valueOf((String)maxDepthList.get(i));
               }
               size = depthList.size();
               depthArray = new Integer[size];
               for ( int i = 0; i < size; i++ )
                depthArray[i] = Integer.valueOf((String)depthList.get(i));

           } catch ( Exception e ) {
             e.printStackTrace();
             throw new PortalException(e.getMessage());
            }

         }

         /**
           * Checks the restriction for the specified node
           * @param propertyValue a <code>String</code> property value to be checked
           * @exception PortalException
         */
         public boolean checkRestriction(String propertyValue) throws PortalException {
           int depth = CommonUtils.parseInt(propertyValue);
           for ( int i = 0; i < minDepthArray.length || i < depthArray.length; i++ ) {
             if ( i < minDepthArray.length )
               if ( depth <= maxDepthArray[i].intValue() && depth >= minDepthArray[i].intValue() )
                 return true;
             if ( i < depthArray.length )
              if ( depthArray[i].intValue() == depth )
                 return true;
           }
              return false;
         }

         /**
           * Checks the restriction for the specified node
           * @exception PortalException
         */
         /*public boolean checkRestriction( UserLayoutNode node ) throws PortalException {
           int depth = node.getDepth();
           for ( int i = 0; i < minDepthArray.length || i < depthArray.length; i++ ) {
             if ( i < minDepthArray.length )
               if ( depth <= maxDepthArray[i].intValue() && depth >= minDepthArray[i].intValue() )
                 return true;
             if ( i < depthArray.length )
              if ( depthArray[i].intValue() == depth )
                 return true;
           }
              return false;
         }*/



}