/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.action.executer;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.ActionImpl;
import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.BaseSpringTest;
import org.alfresco.util.GUID;

/**
 * Is sub class evaluator test
 * 
 * @author Roy Wetherall
 */
public class SetPropertyValueActionExecuterTest extends BaseSpringTest
{
    private NodeService nodeService;
    private StoreRef testStoreRef;
    private NodeRef rootNodeRef;
    private NodeRef nodeRef;
    private SetPropertyValueActionExecuter executer;
    
    private final static String ID = GUID.generate();
    
    private final static String TEST_VALUE = "TestValue";

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        this.nodeService = (NodeService)this.applicationContext.getBean("nodeService");
        
        AuthenticationComponent authenticationComponent = (AuthenticationComponent)applicationContext.getBean("authenticationComponent");
        authenticationComponent.setCurrentUser(authenticationComponent.getSystemUserName());
        
        // Create the store and get the root node
        this.testStoreRef = this.nodeService.createStore(
                StoreRef.PROTOCOL_WORKSPACE, "Test_"
                        + System.currentTimeMillis());
        this.rootNodeRef = this.nodeService.getRootNode(this.testStoreRef);

        // Create the node used for tests
        this.nodeRef = this.nodeService.createNode(
                this.rootNodeRef,
                ContentModel.ASSOC_CHILDREN,
                QName.createQName("{test}testnode"),
                ContentModel.TYPE_CONTENT).getChildRef();
        
        // Get the executer instance 
        this.executer = (SetPropertyValueActionExecuter)this.applicationContext.getBean(SetPropertyValueActionExecuter.NAME);
    }
    
    /**
     * Test execution
     */
    public void testExecution()
    {
        // Execute the action
        ActionImpl action = new ActionImpl(null, ID, SetPropertyValueActionExecuter.NAME, null);
        action.setParameterValue(SetPropertyValueActionExecuter.PARAM_PROPERTY, ContentModel.PROP_NAME);
        action.setParameterValue(SetPropertyValueActionExecuter.PARAM_VALUE, TEST_VALUE);
        this.executer.execute(action, this.nodeRef);
        
        // Check that the property value has been set
        assertEquals(TEST_VALUE, this.nodeService.getProperty(this.nodeRef, ContentModel.PROP_NAME));
        
        // Check what happens when a bad property name is set
        action.setParameterValue(SetPropertyValueActionExecuter.PARAM_PROPERTY, QName.createQName("{test}badProperty"));
        
        try
        {
            this.executer.execute(action, this.nodeRef);
            fail("We would expect and exception to be thrown since the property name is invalid.");
        }
        catch (Throwable exception)
        {
            // Good .. we where expecting this
        }
    }
}
