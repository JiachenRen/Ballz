package jui_lib.bundles;

import jui_lib.Label;
import jui_lib.ProgressBar;
import jui_lib.VBox;
import processing.core.PConstants;

import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.DOWN;

/**
 * Created by Jiachen on 29/04/2017.
 */
public class ProgressIndicator extends VBox {
    private Label titleLabel;
    private Label descriptionLabel;
    private ProgressBar progressBar;
    private int fakeDelayMillis;
    private boolean applyFakeDelay;

    public ProgressIndicator(String id, float x, float y, float w, float h) {
        super(id, x, y, w, h);
        init();
    }

    public ProgressIndicator(String id, float relativeW, float relativeH) {
        super(id, relativeW, relativeH);
        init();
    }

    public ProgressIndicator(String id) {
        super(id);
        init();
    }

    public void init() {
        super.init();
        setApplyFakeDelay(false);
        this.setMargins(0, 0);
        this.setContainerVisible(false);
        this.fakeDelayMillis = 1000;

        titleLabel = (Label) new Label("titleLabel", 1.0f, 0.33f)
                .setContent("Progress Indicator")
                .setAlign(CENTER);


        progressBar = (ProgressBar) new ProgressBar("progressBar", 1.0f, 0.33f)
                .setPercentageTextStyle(ProgressBar.Style.MIDDLE).setTextColor(255);


        descriptionLabel = new Label("descriptionLabel", 1.0f, 0.33f)
                .setContent("Description for what's going on");

        this.add(titleLabel);
        this.add(progressBar);
        this.add(descriptionLabel);
        this.setAlignV(PConstants.DOWN);
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(Label titleLabel) {
        this.titleLabel = titleLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabel(Label descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setTitle(String title) {
        titleLabel.setContent(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setContent(description);
    }

    public void setCompletedPercentage(float percentage) {
        progressBar.setCompletedPercentage(percentage);
        if (applyFakeDelay) {
            try {
                Thread.sleep((int) (Math.random() * fakeDelayMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setApplyFakeDelay(boolean temp) {
        applyFakeDelay = temp;
    }

    public void setFakeDelayMillis(int temp) {
        fakeDelayMillis = temp;
    }

    public void incrementPercentage(float val) {
        this.setCompletedPercentage(progressBar.getPercentageCompleted() + val);
    }

}