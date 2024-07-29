# Welcome to GetEmotion!

**GetEmotion** is an Android mobile application used as a Haptic Touch Data Collector tool as part of the methodologies of the thesis, Integrating Haptic Touch on Android Devices for Emotion Recognition in an Emotionally Stimulated Environment. It is mainly built following the experiment procedure of collecting haptic data from participants who were emotionally stimulated using the LIRIS-ACCEDE dataset. 

GetEmotion is developed using the *Kotlin* programming language in Android Studio, with the integration of Firebase Firestore and Authentication. 

The next section details how to run GetEmotion on an Android smartphone. It is recommended that the open-source project is run only through Android Studio, and that an external smartphone device is ready for usage.

# Guide
The guide explains the compatibility, installations, and step-by-step process in order to run GetEmotion application in an Android Smartphone. Please check each of the subsections below:

 1. **Smartphone Compatibility**
 2. **Android Studio Processes**
 3. **Firebase Processes**
 4. **Running the Application**

## Smartphone Compatibility
By default, GetEmotion application will only work on at least **SDK 30/Android Platform Version 11** due to its capability for wireless debugging, which is used to run the app on a smartphone without needing a connecting wire. 

Please ensure that the Android version of your device uses at least ***Android 11***.

## Android Studio Processes
Download the latest version of [Android Studio](https://developer.android.com/studio). By the initial version of this application, GetEmotion is runnable on Android Studio Koala 2024.1.1. It also runs on Gradle 8.5 as per project publishing.


## Firebase Processes
GetEmotion highly depends on using Firebase Firestore and Authentication for its data management and storage. **Please follow the instructions below to ensure that the application is runnable without errors**:


### Account Creation

#### Go to [Firebase Console](https://console.firebase.google.com/) and create an account.

![image](https://github.com/user-attachments/assets/32f75d39-cd47-4829-99bc-398b4190e239)

#### Create a Project

1. Click create a project.

![image](https://github.com/user-attachments/assets/19c88eb7-d589-4cbd-9796-b60bbe34451c)

2. Set project name to **GetEmotion**.
   
![image](https://github.com/user-attachments/assets/a04942ff-ef03-4d4b-ad7f-e5fe437b5515)

3. Proceed to next until you confirm your creation.
   
![image](https://github.com/user-attachments/assets/4e25443f-3506-4a62-ae75-951847b6979a)
![image](https://github.com/user-attachments/assets/c3cb053e-345f-4b9f-91a5-669469ce6129)


#### Connect GetEmotion application to Firebase through a series of instructions in the screenshots below:

1. Click the Android icon as shown.
   
![image](https://github.com/user-attachments/assets/47f34373-14b4-4164-815e-a4b80fd6765a)

2. On the *Android package name* field, type **com.opensource.getemotion**.
   
![image](https://github.com/user-attachments/assets/4a14ab51-1ff4-451f-8f61-5665274aacef)

3. Download the **google-services.json** file.
   
![image](https://github.com/user-attachments/assets/9646d591-98ed-4aba-8ac3-7097f71c867b)

4. In the next steps, click next until the setup is finished.
   
![image](https://github.com/user-attachments/assets/683f3488-00e3-476b-8e17-f0d3012f1c50)
![image](https://github.com/user-attachments/assets/a365fc7f-cfc9-44c6-bbbf-472d651f65ec)


#### Configure Firebase Firestore through these instructions:

1. Find the **Firestore Database**.
   
![image](https://github.com/user-attachments/assets/e17216d8-fd96-49b4-8ef1-604109d66ff8)

2. (Optional) You can set a location based on your preference.
   
![image](https://github.com/user-attachments/assets/c8091eee-c044-4cbb-8202-b82947b0be21)

3. Choose **Start in Test Mode**. Please don't use production mode.
   
![image](https://github.com/user-attachments/assets/e1314dc6-2c41-430a-9cf3-3a20a321c511)

4. Click the **Rules** tab.
   
![image](https://github.com/user-attachments/assets/205a70cb-c3ac-4be0-b9c2-078ee00a26b6)

5. Set the *timestamp.date()* to a date of your preference, and click publish.
   
![image](https://github.com/user-attachments/assets/28e5c260-073e-4d10-a365-272eba778cd5)

6. It is crucial to add two collections in order for GetEmotion to work: **Users** and **Interactions**. Click **Start collection**.
   
![image](https://github.com/user-attachments/assets/2c1febc2-ede0-43eb-a0c8-885f108213ff)

7. Firstly, enter **Users** for the collection ID.
    
![image](https://github.com/user-attachments/assets/8cdc4e1e-fcb8-4f39-8f90-f084cd6522d0)

8. Then, you can indicate the document ID as **Test** for a test document. Click save.
    
![image](https://github.com/user-attachments/assets/327e427e-a4fa-4ff6-b425-ef6ae00ca171)

9. Repeat Steps 6 to 8 for the **Interactions** collection.
    
![image](https://github.com/user-attachments/assets/56cf2535-165b-4b17-be48-ec6fcc850bf0)



#### Configure Firebase Authentication by following these steps:

1. Find the **Authentication**.

![image](https://github.com/user-attachments/assets/541dd8b8-9bd7-4351-8eae-4adbb12db192)

2. Under Native Providers, choose **Email/Password**.
   
![image](https://github.com/user-attachments/assets/ccf8ac4e-ab23-4873-a57e-b994a84a98ce)

3. **Only enable Email/Password**. Then, click save.

![image](https://github.com/user-attachments/assets/6a8e8d95-3c1e-4eee-b505-017c40b02f2d)


### Running the Program

#### Move **google-services.json** to your module (app-level) root directory

1. Make sure that you are under **Project view**.
2. Move the **google-services.json** under the **app folder**. 

![image](https://github.com/user-attachments/assets/7461f621-7c19-4db1-8f75-1720633bad88)

#### Connect your device to Android Studio

1. Find the **Device Manager** button at the top of the Android Studio IDE. It can also be found on the right side of Android Studio.
2. Click **Pair Devices Using WiFi** to pair your device wireless.
   
![image](https://github.com/user-attachments/assets/16fd39b4-c08f-4f99-82b3-826e7522a5d1)

3. On your device, enable **Developer Options**. Then, go to **Wireless Debugging**.
4. You can pair using a QR code or a pairing code.
   
![image](https://github.com/user-attachments/assets/cc74cc36-f1de-43b3-9da3-8ec40379d4ca)

***Note:*** You can also connect your device using a connector wire by enabling **USB Debugging** under the Developer Options.

### Running the Application

To run the GetEmotion application, click the **Run** button at the top of the Android Studio IDE or go to the **Run** tab. 

![image](https://github.com/user-attachments/assets/df8b5bfe-5f76-4836-948a-d01d049c1e5d)


