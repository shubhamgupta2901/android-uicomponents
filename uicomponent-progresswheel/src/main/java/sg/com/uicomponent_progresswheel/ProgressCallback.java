package sg.com.uicomponent_progresswheel;

public interface ProgressCallback {
    /**
     * Method to call when the progress reaches a value
     * in order to avoid float precision issues, the progress
     * is rounded to a float with two decimals.
     *
     * In indeterminate mode, the callback is called each time
     * the wheel completes an animation cycle, with, the progress value is -1.0f
     *
     * @param progress a double value between 0.00 and 1.00 both included
     */
    public void onProgressUpdate(float progress);
  }