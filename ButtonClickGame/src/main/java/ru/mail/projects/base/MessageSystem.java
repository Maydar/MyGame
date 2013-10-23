package ru.mail.projects.base;


import java.util.Queue;

public interface MessageSystem  {
	public void addService (Abonent abonent);
	public void sendMessage (Msg message);
	public void execForAbonent (Abonent abonent);
	public AddressService getAddressService ();
    public Queue<Msg> getQueue(Address address);
}
