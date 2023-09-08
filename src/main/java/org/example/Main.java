package org.example;

import org.apache.log4j.Logger;
import org.example.data.Member;
import org.example.data.OsbbCrud;

import java.io.IOException;
import java.util.Optional;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("The program has started");
        logger.info("Flyway migration execute");
        try (OsbbCrud crud = new OsbbCrud()
                .init()) {
        for (Member member: crud.getMembersWithAutoNotAllowed()){
            final StringBuffer sb= new StringBuffer();
            sb.append(member.getId())
                    .append(" : ")
                    .append(member.getName())
                    .append(" : ")
                    .append("\r\n");
            System.out.println(sb);
        }
        final Optional <Member> m3 = crud.getMemberById(3);
        logger.trace(m3.get());
        } catch (IOException e) {
            logger.fatal(e);
        }
    }


}