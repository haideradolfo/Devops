package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.schedular;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduledMethods {
    private final ClientRepository clientRepository; // ← GARDER SEULEMENT ÇA

    @Scheduled(fixedRate = 2000)
    public void method1() {
        log.info("method1 called");
    }

    @Scheduled(fixedDelay = 2000)
    public void method2() {
        log.info("method2 called");
    }

    @Scheduled(cron = "15 * * * * *")
    public void method3() {
        log.info("method3 called");
    }

    @Scheduled(cron = "*/15 * * * * *")
    public void method4() {
        log.info("method4 called");
    }

    @Scheduled(cron = "0/15 * * * * *")
    public void method5() {
        log.info("method5 called");
    }

    @Scheduled(cron = "0 0/30 11 * * *")
    public void method6() {
        log.info("method6 called");
    }

    @Scheduled(cron = "0 0 8 ? 4 ?")
    public void method7() {
        log.info("method7 called");
    }

    @Scheduled(cron = "0 0 9 14 2 SUN,TUE")
    public void method8() {
        log.info("method8 called");
    }

    // 1. ANNIVERSAIRE - Tous les jours à minuit
    @Scheduled(cron = "0 * * * * *")
    public void bonusAnniversaire() {
        log.info("BONUS ANNIVERSAIRE - Debut");
        log.info("Date: " + LocalDate.now());

        LocalDate aujourdhui = LocalDate.now();
        List<Client> tousClients = clientRepository.findAll();
        int anniversaires = 0;

        for (Client client : tousClients) {
            LocalDate dateNaissance = client.getDateNaissance();

            if (dateNaissance != null &&
                    dateNaissance.getDayOfMonth() == aujourdhui.getDayOfMonth() &&
                    dateNaissance.getMonthValue() == aujourdhui.getMonthValue()) {

                anniversaires++;
                CarteFidelite carte = client.getCarteFidelite();

                if (carte != null) {
                    int anciens = carte.getPointsAccumules(); // int, pas null
                    int nouveaux = anciens + (anciens * 10 / 100);
                    carte.setPointsAccumules(nouveaux);
                    clientRepository.save(client);

                    log.info("Client " + client.getNom() + " " + client.getPrenom() +
                            ": " + anciens + " -> " + nouveaux + " points");
                }
            }
        }

        log.info("Total anniversaires: " + anniversaires);
        log.info("BONUS ANNIVERSAIRE - Fin");
    }

    @Scheduled(cron = "0 */2 * * * *")  // Version test: toutes les 2 minutes
    public void promotionsMensuelles() {
        log.info("PROMOTIONS DU MOIS - Debut");
        log.info("Mois: " + LocalDate.now().getMonth());
        log.info("Articles en promotion:");
        log.info("- Café Arabica: 25% de reduction");
        log.info("- Croissant au beurre: 20% de reduction");
        log.info("- Thé Vert: 15% de reduction");
        log.info("PROMOTIONS DU MOIS - Fin");
    }
}