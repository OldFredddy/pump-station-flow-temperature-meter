package org.csbk;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;




public class Controller {
     @FXML
    private Button startButton;
    @FXML
    void initialize() throws TelegramApiException {
        startButton.setOnAction(actionEvent -> {
            TelegramBotsApi botsApi = null;
            try {
                botsApi = new TelegramBotsApi(DefaultBotSession.class);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            FlowMeterBot flowMeterBot = new FlowMeterBot();
            try {
                botsApi.registerBot(flowMeterBot);
                flowMeterBot.startSendingScreenshots();
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            startButton.setDisable(true);
                });


    }
}