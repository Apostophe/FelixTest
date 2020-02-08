package camix.service;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;

public class ClienChatTextEx2 {
    private ServiceChat serviceChat;
    private ClientChat clientChatMock;

    /*@Before
    public void setUp() throws Exception {
        this.serviceChat = new ServiceChat("test",12345);
        this.clientChatMock = EasyMock.createMock(ClientChat.class);
    }*/

    @Test(timeout = 2000)
    public void testInformeDepartClient() throws IOException {
        this.serviceChat = new ServiceChat("test",12345);
        this.clientChatMock = EasyMock.createMock(ClientChat.class);
        final String clientSurnom = "surnom";
        EasyMock.expect(
                this.clientChatMock.donneSurnom()
        ).andReturn(clientSurnom);
        EasyMock.replay(this.clientChatMock);
        EasyMock.verify();
    }
}
