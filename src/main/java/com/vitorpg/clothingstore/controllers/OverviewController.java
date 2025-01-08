package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.models.Sale;
import com.vitorpg.clothingstore.services.ProductService;
import com.vitorpg.clothingstore.services.SaleService;
import com.vitorpg.clothingstore.services.SupplyService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Locale;

public class OverviewController {

    private SaleService saleService = new SaleService();
    private SupplyService supplyService = new SupplyService();
    private ProductService productService = new ProductService();


    @FXML
    private StackPane spHeader;

    @FXML
    private Label lbIntervalDate;

    @FXML
    private Label lbTotalRevenue;

    @FXML
    private Label lbSoldItems;

    @FXML
    private Label lbTotalProfit;

    @FXML
    private SVGPath svgEye;

    private String[] eyeIconSvgPaths = {
        "M26.5383 22.985L24.5104 20.9571C25.0754 19.2071 24.7004 17.7071 23.3854 16.4571C22.0707 15.2071 20.5754 14.8368 18.8996 15.3463L16.8717 13.3184C17.3439 13.0406 17.8554 12.8368 18.4062 12.7071C18.9574 12.5777 19.5292 12.5129 20.1217 12.5129C22.1308 12.5129 23.8368 13.2143 25.2396 14.6171C26.6424 16.0199 27.3437 17.7259 27.3437 19.735C27.3437 20.3275 27.2743 20.9039 27.1354 21.4642C26.9965 22.0245 26.7975 22.5314 26.5383 22.985ZM31.8996 28.3184L29.9829 26.4296C31.2421 25.4943 32.3462 24.4549 33.2954 23.3113C34.2443 22.1679 34.9735 20.9759 35.4829 19.735C34.094 16.7442 32.0407 14.3761 29.3229 12.6309C26.6054 10.8856 23.631 10.0129 20.3996 10.0129C19.3532 10.0129 18.2929 10.1054 17.2187 10.2904C16.1449 10.4757 15.2514 10.7072 14.5383 10.985L12.3996 8.81836C13.4274 8.36475 14.6449 7.98739 16.0521 7.68627C17.4596 7.38544 18.8624 7.23502 20.2604 7.23502C24.3068 7.23502 27.9735 8.37627 31.2604 10.6588C34.5476 12.941 36.9458 15.9664 38.455 19.735C37.7606 21.4664 36.8601 23.0567 35.7537 24.5059C34.6471 25.955 33.3624 27.2259 31.8996 28.3184ZM33.7883 38.0129L26.7883 31.1238C25.8161 31.4849 24.7582 31.7604 23.6146 31.9504C22.4712 32.1402 21.3069 32.235 20.1217 32.235C16.0197 32.235 12.3207 31.0938 9.02458 28.8113C5.72819 26.5291 3.31611 23.5036 1.78833 19.735C2.35305 18.2814 3.11236 16.8856 4.06625 15.5475C5.01986 14.2095 6.15875 12.9571 7.48291 11.7904L2.455 6.73502L4.39958 4.76294L35.6496 36.0129L33.7883 38.0129ZM9.39958 13.735C8.44569 14.4759 7.53819 15.3971 6.67708 16.4988C5.81597 17.6007 5.16791 18.6795 4.73291 19.735C6.14014 22.7259 8.22569 25.0939 10.9896 26.8392C13.7535 28.5845 16.8715 29.4571 20.3437 29.4571C21.1401 29.4571 21.9226 29.4085 22.6912 29.3113C23.4596 29.2141 24.0846 29.0775 24.5662 28.9017L22.2883 26.5963C21.9828 26.7166 21.6356 26.8068 21.2467 26.8671C20.8578 26.9271 20.4828 26.9571 20.1217 26.9571C18.1308 26.9571 16.4294 26.2604 15.0175 24.8671C13.6056 23.4735 12.8996 21.7628 12.8996 19.735C12.8996 19.3553 12.9296 18.9803 12.9896 18.61C13.0499 18.2397 13.1401 17.8925 13.2604 17.5684L9.39958 13.735Z",
        "M36.0732 42.9104C38.0802 42.9104 39.785 42.2079 41.1878 40.8029C42.5906 39.3982 43.292 37.6922 43.292 35.685C43.292 33.6781 42.5895 31.9732 41.1845 30.5704C39.7798 29.1677 38.0738 28.4663 36.0666 28.4663C34.0596 28.4663 32.3548 29.1688 30.952 30.5738C29.5492 31.9785 28.8478 33.6845 28.8478 35.6917C28.8478 37.6986 29.5503 39.4035 30.9553 40.8063C32.36 42.209 34.066 42.9104 36.0732 42.9104ZM36.0632 40.2996C34.7807 40.2996 33.6927 39.8506 32.7991 38.9525C31.9055 38.0547 31.4587 36.9645 31.4587 35.6817C31.4587 34.3992 31.9077 33.3111 32.8057 32.4175C33.7035 31.5239 34.7938 31.0771 36.0766 31.0771C37.3591 31.0771 38.4471 31.5261 39.3407 32.4242C40.2344 33.322 40.6812 34.4122 40.6812 35.695C40.6812 36.9775 40.2321 38.0656 39.3341 38.9592C38.4363 39.8528 37.346 40.2996 36.0632 40.2996ZM36.0699 48.1884C32.0144 48.1884 28.3384 47.0425 25.042 44.7509C21.7459 42.4592 19.3107 39.4384 17.7366 35.6884C19.3107 31.9384 21.7459 28.9175 25.042 26.6259C28.3384 24.3342 32.0144 23.1884 36.0699 23.1884C40.1255 23.1884 43.8014 24.3342 47.0978 26.6259C50.3939 28.9175 52.8291 31.9384 54.4032 35.6884C52.8291 39.4384 50.3939 42.4592 47.0978 44.7509C43.8014 47.0425 40.1255 48.1884 36.0699 48.1884ZM36.0653 45.4104C39.3553 45.4104 42.3777 44.5285 45.1324 42.7646C47.8871 41.0007 49.9867 38.642 51.4312 35.6884C49.9867 32.7347 47.8887 30.376 45.137 28.6121C42.3856 26.8482 39.3648 25.9663 36.0745 25.9663C32.7845 25.9663 29.7621 26.8482 27.0074 28.6121C24.2527 30.376 22.1439 32.7347 20.6812 35.6884C22.1439 38.642 24.2512 41.0007 27.0028 42.7646C29.7542 44.5285 32.775 45.4104 36.0653 45.4104Z"
    };

    private Point2D.Double[] eyeIconOffsets = {
        new Point2D.Double(0, 2),
        new Point2D.Double(0, 0)
    };

    private boolean isHidden = false;

    @FXML
    public void initialize () {
        toggleStatistics();
        Image image = new Image(getClass().getResource("/com/vitorpg/clothingstore/images/header-background.png").toExternalForm());

        BackgroundImage background = new BackgroundImage(
                image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        image.getWidth() * 0.3,
                        image.getHeight() * 0.3,
                        false,
                        false,
                        false,
                        false
                ));
        spHeader.setBackground(new Background(background));
        Rectangle clip = new Rectangle(background.getSize().getWidth(), background.getSize().getHeight());
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        spHeader.setClip(clip);
        spHeader.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            clip.setWidth(newBounds.getWidth());
            clip.setHeight(newBounds.getHeight());
        });
    }

    private void showStatistics () {
        try {
            lbTotalRevenue.setText(String.format(Locale.US, "R$ %.2f",
                    saleService.getTotalRevenue()));

            lbSoldItems.setText(saleService.getSoldItemsCount().toString());

            lbTotalProfit.setText(String.format(Locale.US, "R$ %.2f",
                    saleService.getTotalProfit()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void hideStatistics () {
        try {
            lbTotalRevenue.setText("R$ XXXX.XX");

            lbSoldItems.setText("XXX");

            lbTotalProfit.setText("R$ XXXX.XX");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void toggleStatistics () {
        isHidden = !isHidden;
        int index = isHidden? 0 : 1;
        svgEye.setContent(eyeIconSvgPaths[index]);
        svgEye.setTranslateX(eyeIconOffsets[index].x);
        svgEye.setTranslateY(eyeIconOffsets[index].y);

        if(isHidden)
            hideStatistics();
        else
            showStatistics();
    }
}
