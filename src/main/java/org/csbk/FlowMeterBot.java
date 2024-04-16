package org.csbk;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class FlowMeterBot extends TelegramLongPollingBot {

    private String chatId = "@BoilersAnadyr";
    private Integer messageId = null;

    @Override
    public void onUpdateReceived(Update update) {
        // Implementation for incoming update handling
    }

    @Override
    public String getBotUsername() {
        return "pump_station_aksu_bot";
    }

    @Override
    public String getBotToken() {
        return "7088257853:AAG02KN0RmMaG2uRVCRT4e7wTTGxqOOb8Y4";
    }

    public void startSendingScreenshots() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendOrUpdateScreenshot();
                } catch (TelegramApiException | AWTException | IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 60000); // schedule the task to run starting now and then every 60 seconds
    }

    private void sendOrUpdateScreenshot() throws AWTException, IOException, TelegramApiException {
        BufferedImage screenCapture = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        File screenshotFile = new File("screenshot.png");
        ImageIO.write(screenCapture, "png", screenshotFile);

        InputFile photo = new InputFile(screenshotFile);

        if (messageId == null) {
            SendPhoto sendPhoto = new SendPhoto(chatId, photo);
            messageId = execute(sendPhoto).getMessageId();
        } else {
            InputMediaPhoto mediaPhoto = new InputMediaPhoto();
            mediaPhoto.setMedia(photo.getNewMediaFile(), "screenshot.png");
            EditMessageMedia editMessageMedia = new EditMessageMedia();
            editMessageMedia.setChatId(chatId);
            editMessageMedia.setMessageId(messageId);
            editMessageMedia.setMedia(mediaPhoto);
            execute(editMessageMedia);
        }
    }
}
