package com.agrc.service.emails;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class serviceMail {

    private static final Object SessionMail = null;
    public Object typeSearch ;
    public boolean protectMail;
    public Integer numberMessage;
    public boolean isHidden;
    private Message[] messages ;

    public static String defaultSenderEmail = "your-email";
    public static String emailPassword = "you-password" ;
    public static final String host = "your-protocol";

    public Integer offset ;
    public Integer numPages ;
    public String folder ;
    public Integer page ;
    public Message message ;
    public String subjectMail ;
    public String toMail ;
    public String ccMail ;
    public String bccMail ;
    public File[] attachments ;
    public String bodyMail ;

    private static String port = "587" ;
//    private String port = "993" ;
//    private String port = "465" ;
//    private String port = "25" ;
    private String auth = "true" ;
    private String protocol = "imap" ;

    public static Properties getServerProperties() {
        Properties props = new Properties() ;

        props.setProperty("mail.smtp.host", host ) ;
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty( "mail.smtp.starttls.enable", "true" ) ;
        props.put("mail.smtp.EnableSSL.enable","true");
        props.setProperty( "mail.smtp.port", port ) ;
        props.put("mail.smtp.ssl.trust", host ) ;

        return props ;
    }

    public Message[] getMails() {
        try {
            Properties props = getServerProperties() ;
            Session session = Session.getDefaultInstance( props, null ) ;

            Store store = session.getStore( protocol ) ;
            try {
                store.connect( this.host , defaultSenderEmail , emailPassword ) ;


                Folder emailFolder = store.getFolder( this.folder ) ;
                emailFolder.open( Folder.READ_ONLY ) ;

                Message[] messages = emailFolder.getMessages() ;
                this.numPages = messages.length / 10 ;

                if ( messages != null ) {
//                    for (int i = 0, n = messages.length; i < n; i++) {
//                        javax.mail.Message message = messages[ i ] ;
//    //                    list.add( messages[ i ] ) ;
//                        System.out.println( "Email Number " + ( i + 1 ) ) ;
//                        System.out.println( "Subject: " + message.getSubject() ) ;
//                        System.out.println( "From: " + message.getFrom()[0] ) ;
//                        System.out.println( "Text: " + message.getContent().toString() ) ;
//                        System.out.println( "---------------------------------" ) ;
//                    }
//                    System.out.println( "messages: "+ messages ) ;

                    return messages ;
                }

                emailFolder.close(false ) ;
                store.close() ;
                return null ;
            } catch ( Exception e ) {
                System.out.println( "ERROR getMails(): "+ e.getMessage() ) ;
            }
        } catch ( NoSuchProviderException e ) {
            e.printStackTrace() ;
        } catch ( MessagingException e ) {
            e.printStackTrace() ;
        } catch ( Exception e ) {
            System.out.println( "ERROR: "+ e.getMessage() ) ;
        }

        return null ;
    }

//    public List getMailsJSON() {
//        try {
//            Properties props = getServerProperties() ;
//            Session session = Session.getDefaultInstance( props, null ) ;
//
//            Store store = session.getStore( protocol ) ;
//            try {
//                store.connect(this.host, defaultSenderEmail.email, defaultSenderEmail.emailPassword);
//                String jsonListMails = null ;
//
//                Folder emailFolder = store.getFolder( this.folder ) ;
//                emailFolder.open( Folder.READ_ONLY ) ;
//
//                Message[] messages = emailFolder.getMessages() ;
//
//                ArrayList<Object> listMails = new ArrayList<Object>();
//                if ( messages != null ) {
////                    for (int i = 0, n = messages.length; i < n; i++) {
//                    int i = 0 ;
//                    for ( Message msg: messages ) {
//                        MailDatatable mdt = new MailDatatable() ;
//
////                        listMails.add( i , messages[ i ].getSubject() ) ;
////                        listMails.add( i , messages[ i ].getFrom()[ 0 ] ) ;
////                        listMails.add( i , messages[ i ].getContent().toString() ) ;
//
//                        String from = null ;
//                        for( Address m: msg.getFrom() ) {
//                            from = m.toString() ;
//
//                        }
//
////                        System.out.println( "From: "+ from ) ;
////                        System.out.println( "Subject: "+ msg.getSubject() ) ;
////                        System.out.println( "Date: "+ msg.getReceivedDate() ) ;
////                        System.out.println( "--------------------------------------------------" ) ;
//
//                        mdt.setFrom( from ) ;
//                        mdt.setSubject( msg.getSubject() ) ;
//                        mdt.setDate( String.valueOf( msg.getReceivedDate() ) ) ;
//                        mdt.setMessageNumber( String.valueOf( msg.getMessageNumber() ) ) ;
//
//                        listMails.add( i , mdt ) ;
//                        i++;
//                    }
//
//                    return listMails ;
//                }
//
//            } catch ( Exception e ) {
//                System.out.println( "ERROR getMails(): "+ e.getMessage() ) ;
//            }
//        } catch ( NoSuchProviderException e ) {
//            e.printStackTrace() ;
//        } catch ( MessagingException e ) {
//            e.printStackTrace() ;
//        } catch ( Exception e ) {
//            System.out.println( "ERROR: "+ e.getMessage() ) ;
//        }
//
//        return null ;
//    }


    public Message[] Single( String folder, Integer mailId ) throws MessagingException, IOException {
        try {
            Properties props = getServerProperties() ;
            Session session = Session.getDefaultInstance( props, null ) ;

            Store store = session.getStore( protocol ) ;
            try {
                store.connect( this.host , defaultSenderEmail , emailPassword ) ;
            } catch ( Exception e ) {
                System.out.println( "ERROR Single(): "+ e.getMessage() ) ;
            }

            Folder emailFolder = store.getFolder( folder ) ;
            emailFolder.open( Folder.READ_ONLY ) ;

            Message[] messages = emailFolder.getMessages( mailId, mailId ) ;

            if ( messages != null ) {
//                emailFolder.close(false ) ;
//                store.close() ;
                return messages ;
            }

            emailFolder.close(false ) ;
            store.close() ;
            return null ;

        } catch ( NoSuchProviderException e ) {
            e.printStackTrace() ;
        } catch ( MessagingException e ) {
            e.printStackTrace() ;
        } catch ( Exception e ) {
            System.out.println( "ERROR aqui: "+ e.getMessage()+ " | "+ e.getStackTrace() ) ;
        }

        return null ;
    }




    public serviceMail sendMail() {
        try {
            Properties props = getServerProperties() ;
            Session session = Session.getDefaultInstance( props, null ) ;

            BodyPart texto = new MimeBodyPart() ;
            texto.setContent( bodyMail, "text/html" ) ;

            MimeMultipart multiParte = new MimeMultipart() ;
            multiParte.addBodyPart(texto);

            if (attachments != null) {
                for (File attachment : attachments) {
                    MimeBodyPart attachmentBodyPart= new MimeBodyPart() ;
                    DataSource source = new FileDataSource( attachment ) ;
                    attachmentBodyPart.setDataHandler( new DataHandler( source ) ) ;
                    attachmentBodyPart.setFileName( String.valueOf( attachment ) ) ;

                    multiParte.addBodyPart( attachmentBodyPart ) ;
                }
            }

            MimeMessage msg = new MimeMessage( session ) ;
            msg.setFrom( new InternetAddress( defaultSenderEmail ) ) ;

            if ( StringUtils.isNotBlank( toMail ) ) {
                String[] toList = toMail.split( "," ) ;
                for ( int i = 0 ; i < toList.length ; i++ ) {
                    msg.addRecipient(
                            MimeMessage.RecipientType.TO
                            , new InternetAddress( toList[ i ] )
                    ) ;
                }
            }

            if ( StringUtils.isNotBlank( ccMail ) ) {
                String[] toList = ccMail.split( "," ) ;
                for ( int i = 0 ; i < toList.length ; i++ ) {
                    msg.addRecipient(
                            MimeMessage.RecipientType.CC
                            , new InternetAddress( toList[ i ] )
                    ) ;
                }
            }

            if ( StringUtils.isNotBlank( this.bccMail ) ) {
                String[] toList = this.bccMail.split( "," ) ;
                for ( int i = 0 ; i < toList.length ; i++ ) {
                    msg.addRecipient(
                            MimeMessage.RecipientType.BCC
                            , new InternetAddress( toList[ i ] )
                    ) ;
                }
            }

            msg.setSubject( this.subjectMail ) ;
            msg.setContent( multiParte ) ;

            Transport t = session.getTransport( "smtp" ) ;
//            Transport t = session.getTransport("smtps");

            t.connect( this.host, defaultSenderEmail, emailPassword ) ;
            t.sendMessage( msg, msg.getAllRecipients() ) ;
            t.close() ;

            Store store = (Store) session.getStore("imap") ;
            store.connect(host, defaultSenderEmail,emailPassword);
            Folder email2Sent = store.getFolder("INBOX.Sent" ) ;

            Flags SEEN = new Flags( "SEEN" ) ;
            msg.setFlags( SEEN, true ) ;

            Flags PROCESSED = new Flags( "PROCESSED" ) ;
            msg.setFlags( PROCESSED, true ) ;

            email2Sent.open(Folder.READ_WRITE) ;
            email2Sent.appendMessages( new MimeMessage[] { msg } ) ;
            email2Sent.close(false ) ;

            store.close() ;

            System.out.println( "Correo ENVIADO "+toMail );

        } catch( Exception e ) {
            System.out.println( "ERROR to "+toMail+" ( catch->sendMail() ): "+ e.getMessage() ) ;
            e.printStackTrace() ;

        }

        return this ;
    }











    public String getTextFromMimeMultipart( MimeMultipart mimeMultipart ) {
        try {
            String result = "";
            int partCount = mimeMultipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = html;
                } else if (bodyPart.getContent() instanceof MimeMultipart) {
                    result = result + getTextFromMimeMultipart( ( MimeMultipart ) bodyPart.getContent() ) ;
                }
            }

            return result;
        } catch ( Exception e ) {
            e.getStackTrace() ;
        }

        return null ;
    }


    public Folder[] getFolderList() throws MessagingException {
        Properties props = getServerProperties() ;
        Session session = Session.getDefaultInstance( props, null ) ;

        Store store = session.getStore( protocol ) ;

        try {
            store.connect(this.host, defaultSenderEmail, emailPassword);
            Folder[] folders = store.getDefaultFolder().list("*");
//            for (javax.mail.Folder folder : folders) {
//                if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
//                    System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
//                }
//            }

            return folders ;
        } catch (MessagingException e) {
            System.out.println( "ERROR getFolderList(): "+ e.getMessage() ) ;
        }

        return null ;
    }

    public MimeMessage createAttachMail(Session session,FileOutputStream attachment, String QRFileName, String toMail, String bodyMail, String subjectMail, String ccMail, String bccMail, boolean isHidden, String folder) throws Exception {
        MimeMessage message = new MimeMessage(session);

        // Establecer la información básica del correo

        //Remitente
        message.setFrom(new InternetAddress(this.defaultSenderEmail));

        //Recipiente
        message.setRecipient(Message.RecipientType.TO, new InternetAddress( toMail ));

        // título del correo
        message.setSubject ( subjectMail ) ;

        // Cree el cuerpo del mensaje, para evitar el problema de los caracteres chinos ilegibles en el cuerpo del mensaje, debe usar charset = UTF-8 para especificar la codificación de caracteres
        MimeBodyPart text = new MimeBodyPart();
        text.setContent ( bodyMail , "text / html; charset = UTF-8" ) ;


        // Crear archivo adjunto de correo electrónico
        MimeBodyPart attach = new MimeBodyPart() ;

        DataHandler dh = new DataHandler(new FileDataSource("GA-QR/" + QRFileName + ".png" ) ) ;
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        // Crea un contenedor para describir la relación de datos
        MimeMultipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.addBodyPart(attach);
            mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        // Escribe el correo electrónico creado en el almacenamiento en disco E
//        message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        // Devuelve el correo generado

        return message;
    }

}