package com.likemessage.server;

import java.sql.SQLException;
import java.util.List;

import com.gifisan.database.DataBaseContext;
import com.gifisan.nio.common.BeanUtil;
import com.gifisan.nio.component.Parameters;
import com.gifisan.nio.component.future.ReadFuture;
import com.gifisan.nio.server.IOSession;
import com.gifisan.nio.server.RESMessage;
import com.likemessage.bean.B_Contact;
import com.likemessage.bean.T_CONTACT;

public class ContactServlet extends LMServlet {

	public static final String	SERVICE_NAME				= ContactServlet.class.getSimpleName();

	public static final String	ACTION_GETCONTACTLISTBYUSERID	= "ACTION_GETCONTACTLISTBYUSERID";
	
	public static final String ACTION_ADD_CONTACT = "ACTION_ADD_CONTACT";

	protected AbstractService getAbstractService(DataBaseContext context) throws SQLException {
		return new ContactService(context);
	}

	protected void doAccept(IOSession session, ReadFuture future, AbstractService _service) throws Exception {

		ContactService service = (ContactService) _service;

		Parameters parameters = future.getParameters();

		String action = parameters.getParameter(ACTION);

		if (ACTION_GETCONTACTLISTBYUSERID.equals(action)) {
			getContactListByUserID(session, future, parameters, service);
		} else if(ACTION_ADD_CONTACT.equals(action)){
			addContact(session, future, parameters, service);
		} else {
			actionNotFound(session, future, _service);
		}
	}

	private void getContactListByUserID(IOSession session, ReadFuture future, Parameters parameters,
			ContactService service) throws Exception {

		Integer userID = session.getAuthority().getUserID();

		List<B_Contact> contactList = service.getContactListByUserID(userID);

		RESMessage message = new RESMessage(0, contactList, null);

		future.write(message.toString());

		session.flush(future);
	}
	
	private void addContact(IOSession session, ReadFuture future, Parameters parameters,
			ContactService service) throws Exception {

		T_CONTACT contact = (T_CONTACT) BeanUtil.map2Object(parameters.getJSONObject("t_contact"), T_CONTACT.class);

		contact.setOwnerID(session.getAuthority().getUserID());
		
		String friendName = parameters.getParameter("friendName");
		
		RESMessage message = service.addContact(contact,friendName);
		
		future.write(message.toString());

		session.flush(future);
	}

}
