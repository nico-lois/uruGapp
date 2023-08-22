package com.example.uruG;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class WPtwilio {

    public static final String ACCOUNT_SID = "AC3bdc0580055ec1290f0362c4d81b1c9b";
    public static final String AUTH_TOKEN = "2061aadda8f7f26202a40b4960db21cc";

    public void enviarMensaje(String numeroDestino) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
            Message message = Message.creator(
                    //new PhoneNumber("whatsapp:" + numeroDestino),
                    new com.twilio.type.PhoneNumber("whatsapp:+59899089392"),
                    //new com.twilio.type.PhoneNumber("whatsapp:+59899881212"),
                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                    "El pedido esta listo para ser levantado en Ururacer. Juan Antonio Rodiguez 1435"

            ).create();

            System.out.println("Mensaje de WhatsApp enviado con SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Error al enviar el mensaje de WhatsApp: " + e.getMessage());
            // Manejar la excepción aquí
        }
    }
}