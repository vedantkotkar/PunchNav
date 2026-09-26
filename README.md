# 🚗 PunchNav - Turn-by-Turn HUD for Tata Punch

**PunchNav** is an Android companion app built specifically for the **Tata Punch** (and Tiago/Altroz) with the factory 8.89 cm (3.5-inch) center infotainment display.

It intercepts Google Maps turn-by-turn navigation alerts and broadcasts them over **Bluetooth AVRCP metadata**, turning the basic monochrome LCD into a heads-up navigation ticker.

---

### ✨ Features
* **Directional Arrows:**
  * Unicode mode: `➜ 200m: Turn Right`, `↑ 4.5km: Straight`, `⮌ U-Turn in 100m`, `⟳ Roundabout`
  * ASCII fallback mode for screen compatibility: `--> 200m: Turn Right`, `^^ Straight`, `|-> Sharp Right`
* **Live ETA & Street Names:** Shows remaining distance, estimated arrival time, and target road on Line 2.
* **100% Maps Priority:** Ignores background music titles during navigation so you always see your next turn.
* **Zero Wire Splicing / Modding:** Runs 100% through standard phone Bluetooth.

---

### 📦 How to Build the APK on GitHub (Automated)

1. Create a new repository on your GitHub account (e.g., `PunchNav`).
2. Run these commands in this directory:
   ```bash
   git add .
   git commit -m "Initial commit for PunchNav"
   git remote add origin https://github.com/YOUR_USERNAME/PunchNav.git
   git branch -M main
   git push -u origin main
   ```
3. Go to the **Actions** tab on your GitHub repository.
4. GitHub will automatically compile the Android app in 1–2 minutes!
5. Click on the completed run or go to **Releases** to download `app-release.apk` directly to your phone.

---

### 📲 How to Install & Use on Phone

1. Install the downloaded `.apk` on your Android phone.
2. Open **PunchNav** and tap **"Grant Notification Access"**.
3. Turn on the car and let your phone connect to the Tata Punch Bluetooth.
4. Start any destination on **Google Maps** — your next turn and arrow will appear on the 3.5" dashboard screen!
