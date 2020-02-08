package camix.service;
import camix.Camix;
import org.junit.*;
import org.easymock.EasyMock;

import java.lang.reflect.Field;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;

public class CanalChatTestEx1EasyMock {
    private ClientChat clientChatMock;
    private CanalChat canalChat;


    @Before
    public void setUp() throws Exception {
        this.canalChat = EasyMock.partialMockBuilder(CanalChat.class).addMockedMethod("estPresent").withConstructor("test").createMock();
        this.clientChatMock = EasyMock.createMock(ClientChat.class);
    }

    @Test
    public void testAjouteClient_non_present() throws Exception{
        final String message = "Ajout client :";
        final String id = "1";
        EasyMock.expect(this.canalChat.estPresent(this.clientChatMock)).andReturn(true);
        EasyMock.expect(
                this.clientChatMock.donneId()
        ).andReturn(
                id
        ).times(1);
        EasyMock.replay(canalChat);
        EasyMock.replay(clientChatMock);
        /* Pour l'accès au field caché (privé) */
        String attributConcerne = "clients";
        Field attribut;

        attribut = CanalChat.class.getDeclaredField(attributConcerne);
        attribut.setAccessible(true);
        Hashtable<String, ClientChat> table = (Hashtable<String, ClientChat>) attribut.get(this.canalChat);
        table.put(clientChatMock.donneId(),clientChatMock);
        Assert.assertEquals(1, table.size());
        Assert.assertTrue(table.contains(this.clientChatMock));
        EasyMock.verify(this.clientChatMock);
    }

    @Test
    public void deux_un_testAjouteClient_non_present() throws Exception{
        final String message = "Ajout client :";
        final String id = "3";
        EasyMock.expect(
                this.clientChatMock.donneId()
        ).andReturn(
                id
        ).times(1);
        EasyMock.replay(clientChatMock);
        /* Pour l'accès au field caché (privé) */
        String attributConcerne = "clients";
        Field attribut;

        attribut = CanalChat.class.getDeclaredField(attributConcerne);
        attribut.setAccessible(true);
        Hashtable<String, ClientChat> table = (Hashtable<String, ClientChat>) attribut.get(this.canalChat);
        table.put(clientChatMock.donneId(),clientChatMock);
        Assert.assertEquals(1, table.size());
        Assert.assertTrue(table.contains(this.clientChatMock));
        EasyMock.verify(this.clientChatMock);
    }

        @Test
    public void Un_Un_testAjouteClient_non_present() {
        final String message = "Ajout client :";
        final String id = "3";
        EasyMock.expect(
                this.clientChatMock.donneId()
        ).andReturn(
                id
        ).times(3);
        EasyMock.replay(clientChatMock);
        this.canalChat.ajouteClient(this.clientChatMock);
        Assert.assertEquals(1,(int)this.canalChat.donneNombreClients());
        Assert.assertTrue(this.canalChat.estPresent(this.clientChatMock));
        EasyMock.verify(this.clientChatMock);
    }

    @Test
    public void Un_Un_testAjouteClient() {
        final String message = "Ajout client :";
        final String id = "3";
        EasyMock.expect(
                this.clientChatMock.donneId()
        ).andReturn(
                id
        ).times(2);
        EasyMock.replay(clientChatMock);
        this.canalChat.ajouteClient(this.clientChatMock);
        Assert.assertEquals(1,(int)this.canalChat.donneNombreClients());
    }
}