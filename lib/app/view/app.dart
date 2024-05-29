import 'package:aws_connect_supervisor_app/app/view/app_router.dart';
import 'package:aws_connect_supervisor_app/l10n/l10n.dart';
import 'package:flutter/material.dart';

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    final myColorScheme = ColorScheme(
      brightness: Brightness.light,
      primary: const Color(0xFF052739), // Dark Blue
      onPrimary: Colors.white,
      secondary: const Color(0xFFFE9F02), // Orange
      onSecondary: Colors.black,
      error: Colors.redAccent,
      onError: Colors.white,
      background: const Color(0xFFF3F4F5), // Light Gray
      onBackground: Colors.black,
      surface: Colors.white,
      onSurface: Colors.black,
      primaryContainer:
          const Color(0xFF052739).withOpacity(0.8), // Dark Blue with opacity
      secondaryContainer:
          const Color(0xFFFE9F02).withOpacity(0.8), // Orange with opacity
    );
    final themeData = ThemeData(
      colorScheme: myColorScheme,
    );
    final router = AppRouter.createRouter();
    return MaterialApp.router(
      debugShowCheckedModeBanner: false,
      routerDelegate: router.routerDelegate,
      routeInformationParser: router.routeInformationParser,
      routeInformationProvider: router.routeInformationProvider,
      theme: themeData,
      localizationsDelegates: AppLocalizations.localizationsDelegates,
      supportedLocales: AppLocalizations.supportedLocales,
    );
  }
}
