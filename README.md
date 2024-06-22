# Aws Connect Supervisor App

## Getting Started 🚀

This project contains 1 flavor:

- development

### Prerequisites

Before running the project, make sure you have the following installed:

- [Flutter SDK](https://flutter.dev/docs/get-started/install) (version ^3.3.0)
- [Dart SDK](https://dart.dev/get-dart) (included with Flutter)

### Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/lbux/aws-connect-supervisor-app
    cd aws-connect-supervisor-app
    ```

2. **Install dependencies:**

    Run the following command to install the required dependencies:

    ```sh
    flutter pub get
    ```

### Running the App

To run the desired flavor, either use the launch configuration in VSCode/Android Studio or use the following commands:

    flutter run --target lib/main_development.dart
    

### Target Device

Make sure to select web as your target device. You can do this by running the following command:

    flutter devices

This will list all available devices. Then, use the following command to run on the web:

    flutter run -d chrome --target lib/main_development.dart

### Backend Integration

The current implementation has logic in `lib/app/services/insight_services.dart` to read from a demo `insights.json` file located in `assets/insight.json` as a fallback method.

To make the app work directly with the [backend](https://github.com/lbux/aws-connect-supervisor-app/tree/backend), follow the instructions in the backend branch and run the frontend code as usual.

_**Aws Connect Supervisor App works on iOS, Android, Web, and Windows.**_

---
