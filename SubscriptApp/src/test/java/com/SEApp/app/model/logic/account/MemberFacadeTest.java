package com.SEApp.app.model.logic.account;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.Member.MemberFacade;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class MemberFacadeTest {

    @Test
    public void testCreateMember() throws SQLException {
        // Création de l'instance de MemberFacade
        MemberFacade memberFacade = MemberFacade.getInstance();

        // Création d'un objet Member
        Member member = new Member("alice@merveille.fr", "Alice Merveille", "12345678", true);

        // Appel de la méthode createMember et vérification du résultat
        boolean result = memberFacade.createMember(member);
        assertTrue(result, "La création du membre a échoué");
    }
}
