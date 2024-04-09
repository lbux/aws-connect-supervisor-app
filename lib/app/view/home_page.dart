import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int currentPageIndex = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      bottomNavigationBar: NavigationBar(
        onDestinationSelected: (int index) {
          currentPageIndex = index;
        },
        destinations: const <Widget>[
          NavigationDestination(
            selectedIcon: Icon(Icons.home),
            icon: Icon(Icons.home_outlined),
            label: 'Home',
          ),
          NavigationDestination(
            selectedIcon: Icon(Icons.people),
            icon: Icon(Icons.people_outlined),
            label: 'Floor',
          ),
        ],
      ),
      body: Row(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: 5,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                    title: Text('Item $index'),
                  ),
                );
              },
            ),
          ),
          const Expanded(
            child: Column(
              children: [
                Expanded(
                  child: Card(
                    child: Center(
                      child: Text('Top Card'),
                    ),
                  ),
                ),
                Expanded(
                  child: Card(
                    child: Center(
                      child: Text('Bottom Card'),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
