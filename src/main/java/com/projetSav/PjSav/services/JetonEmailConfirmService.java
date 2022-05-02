package com.projetSav.PjSav.services;

import com.projetSav.PjSav.dao.JetonEmailConfirmRepository;
import com.projetSav.PjSav.model.JetonEmailConfirm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class JetonEmailConfirmService {

    private JetonEmailConfirmRepository jetonEmailRepository;

    public JetonEmailConfirm findJeton(String jetonConf) {
        return jetonEmailRepository
                .findByJeton(jetonConf)
                .orElseThrow(() -> new RuntimeException("jeton d'émail non trouvé"));
    }

    public void createJeton(JetonEmailConfirm jeton) {
        jetonEmailRepository.save(jeton);
    }

    public void updateMomentConfirmation(String jeton) {
        jetonEmailRepository.updateConfirmedAt(jeton, LocalDateTime.now());
    }


}
