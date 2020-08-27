# ReplayTimelineTransfer

## Requied libraries for building the program yourself

* **[Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5)**
* **[zip4j](http://www.lingala.net/zip4j.html)**

## Functions

* Opening Replay files (`*.mcpr`) and raw timeline files (`*.json`)

* Copying timelines from Replay to Replay

* Moving timelines from Replay to Replay

* Renaming timelines

* Deleting timelines

## How to use

### Deleting timeline(s)

Load your Replay in either side, select your timeline(s) and press the corresponding `Delete` button.

### Renaming timeline(s)

Load your Replay in either side, select your timeline(s) and press the corresponding `Rename` button.

### Copying timeline(s)

Load both of your Replays (one on each side), select the timeline(s) you want to copy to the other Replay, and click the corresponding `Copy` button. If (one of) the timeline(s) you're copying has the same name than one of timelines in the other Replay, a rename prompt will show to rename the copied timeline. If you cancelled/closed this prompt, the timeline will not be copied.

After copying your timeline(s), you can go to `File`, and choose to save both Replays, or one specific side.

If you tried opening a raw timeline file (`*.json`) but an error appeared, make sure it has the correct format. If you're sure it's the correct format but it's still displaying an error, please open an issue and provide me the timeline file + a screenshot of the error.

### Moving timeline(s)

Load both of your Replays (one on each side), select the timeline(s) you want to move to the other Replay, and click the corresponding `Move` button. If (one of) the timeline(s) you're moving has the same name than one of timelines in the other Replay, a rename prompt will show to rename the moved timeline. If you cancelled/closed this prompt, the timeline will not be moved.

After moving your timeline(s), you can go to `File`, and choose to save both Replays, or one specific side.

If you tried opening a raw timeline file (`*.json`) but an error appeared, make sure it has the correct format. If you're sure it's the correct format but it's still displaying an error, please open an issue and provide me the timeline file + a screenshot of the error.
