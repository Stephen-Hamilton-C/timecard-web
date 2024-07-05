# timecard-web

This is a Web frontend for [timecard-lib](https://github.com/Stephen-Hamilton-C/timecard-lib).
Timecard is an application made to help you keep track of the time you're using at work.
Clock in at the start of the day, clock out for lunch, breaks, or end-of-day,
and check out how much time you worked and how long you were on break.

## Features
- Different time formats make it easy to put the time directly into your company's timesheet
- See how long you've been working and how long you've been on break
- See when you are expected to be done with work
- Clock in or out at earlier specific times
- Export a full log of previous time entries to CSV
- 24-hour time option
- Native dark theme

## Usage
Use the app with this link: https://stephen-hamilton-c.github.io/timecard-web/  
This deployment is updated every time the `main` branch is modified.

## Data Storage
Data is stored in browser local storage, so no server is required.
All data is stored locally in web browser data.
If the site data is cleared, all timecard data will be lost.

# For Developers

## Building
1. Install JDK 17 or later
2. Install Google Chrome (not Chromium) and Firefox (Required for testing)
3. `git clone https://github.com/Stephen-Hamilton-C/timecard-lib.git`
4. Publish `timecard-lib` to your local maven repo by running this command in the `timecard-lib` folder
    - `./gradlew publishToMavenLocal` on Linux/macOS
    - `.\gradlew.bat publishToMavenLocal` on Windows
5. Create the browser build with this command in the `timecard-web` folder
    - `./gradlew build browserDistribution` on Linux/macOS
    - `.\gradlew.bat browserDistribution` on Windows
6. Find the resulting build in `./build/distributions`

## Running
1. Run `./gradlew kotlinUpgradeYarnLock` (`.\gradlew.bat kotlinUpgradeYarnLock` on Windows)
2. Run `./gradlew -t run` (`.\gradlew.bat -t run`)

This will open your default web browser to `localhost:8080`.
Any changes made to the code will automatically be built and reloaded in the browser.

# License
timecard-web is licensed under the GNU General Public License v3.0. You can find the license details in the [LICENSE](https://github.com/Stephen-Hamilton-C/timecard-web/blob/main/LICENSE) file in the main branch. Feel free to repurpose, redistribute, and branch off this code anytime. Just keep your modifications open source and licensed the same way :D
