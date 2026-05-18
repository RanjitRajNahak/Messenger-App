# SecureSMS Messenger 💬

SecureSMS is a robust, native Android messenger application built to demonstrate the practical application of core Android system components. The app handles real-time SMS interception, background data processing, structured local storage, and secure data sharing across applications.

## 🚀 Key Features & Android Component Architecture

The application relies heavily on Android's core architectural pillars to deliver a seamless messaging experience:

### 1. Real-Time SMS Interception (`BroadcastReceiver`)
* Utilizes a specialized `BroadcastReceiver` listening for the `android.provider.Telephony.SMS_RECEIVED` intent.
* Automatically parses incoming PDU data packets into readable SMS text messages even when the app is completely closed.

### 2. Immediate Alerts (`Notification System`)
* Triggers a system-level Android `Notification` whenever a new message is intercepted.
* Implements Notification Channels to ensure compliance with modern Android OS standards, allowing users granular control over alert styles.

### 3. Background Processing (`Service`)
* Offloads the heavy lifting of message parsing and formatting to a background `Service`.
* Ensures that the main user interface remains smooth, responsive, and completely stutter-free during heavy incoming traffic.

### 4. Structured Local Storage (`Room Database`)
* Persists message history locally using SQLite via the **Room Persistence Library**.
* Implements type converters and clean data access objects (DAOs) for efficient, thread-safe database queries.

### 5. System Integration (`ContentProvider`)
* Leverages Android's `ContentResolver` to query the device's native system `ContactsContract` database.
* Seamlessly imports and maps device contacts into the app, enabling users to easily pick recipients and send outgoing SMS.

---

## 🏗 Data Flow & Architecture

The app demonstrates a continuous data lifecycle from network interception to UI rendering:

```text
[Incoming SMS] 
      │
      ▼
[BroadcastReceiver] ──(Triggers)──► [Notification System]
      │
      ▼
[Background Service] ──(Writes to)──► [Room SQLite DB] ──► [UI Screen]
                                            ▲
[System Contacts] ──(Fetched via)──► [ContentProvider] ────┘
