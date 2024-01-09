package com.SEApp.app.model.logic.account;

import com.SEApp.app.model.classes.Member;
import com.SEApp.app.model.logic.Member.MemberFacade;
import com.SEApp.app.model.persist.DBAccess.PostGres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class MemberFacadeTest {

    @BeforeAll
    public static void initAll() {
        PostGres db = PostGres.getInstance();
        try {
            db.startBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void endAll() {
        PostGres db = PostGres.getInstance();
        try {
            db.rollbackBigTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
