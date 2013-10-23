package ru.mail.projects.message.system.impl;

import java.util.Map;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Queue;


import ru.mail.projects.base.Abonent;
import ru.mail.projects.base.Address;
import ru.mail.projects.base.AddressService;
import ru.mail.projects.base.MessageSystem;
import ru.mail.projects.base.Msg;

public class MessageSystemImpl implements MessageSystem {
	
	//Карта очередей к каждому объекту (модулю сервера)
	private Map<Address, ConcurrentLinkedQueue<Msg>> messages = 
			new HashMap <Address, ConcurrentLinkedQueue<Msg>>(); 
	private AddressService addressService = new AddressServiceImpl();
	
	public void addService (Abonent abonent) {
		addressService.SetAddress(abonent);
		messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
	}
	
	public void sendMessage (Msg message) {
		//Создаем ссылку на ту очередь, в которую надо записать сообщение
		//В какую очередь записать сообщение определяется адресом получателя
		Queue<Msg> messageQueue = messages.get (message.getTo());  
		messageQueue.add (message);
	}
	
	public void execForAbonent (Abonent abonent) {
		Queue<Msg> messageQueue = messages.get(abonent.getAddress());
		if (messageQueue == null) return;
		while (!messageQueue.isEmpty()) {
			Msg message = messageQueue.poll();
			message.exec(abonent);
		}
	}
	public AddressService getAddressService () {
		return addressService;
	}
}
