package com.cafe24.guestbook.action;

import com.cafe24.mvc.action.AbstractActionFactory;
import com.cafe24.mvc.action.Action;

public class GuestbookActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if( "deleteform".equals( actionName ) ) {
			//action = new DeleteformAction();
		} else if( "delete".equals( actionName ) ) {

		} else if( "add".equals( actionName ) ) {
			
		} else {
			action = new ListAction();
		}
		
		return action;
	}
}
