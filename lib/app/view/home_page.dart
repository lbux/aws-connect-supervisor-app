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
      appBar: AppBar(
        title: const Text('Home Page'),
        centerTitle: true,
        actions: [
          Padding(
            padding: const EdgeInsets.all(8),
            child: IconButton(
              icon: const Icon(Icons.refresh),
              onPressed: () {},
            ),
          ),
        ],
      ),
      body: Row(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: 5,
              itemBuilder: (context, index) {
                final buttonText = (index.isEven) ? 'Agent' : 'Queue';
                return Card(
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      ListTile(
                        title: Text(
                          'Recommendation $index: Click to view details.',
                        ),
                      ),
                      Align(
                        alignment: Alignment.bottomRight,
                        child: Padding(
                          padding: const EdgeInsets.all(8),
                          child: FilledButton.tonal(
                            onPressed: () {},
                            child: Text(buttonText),
                          ),
                        ),
                      ),
                    ],
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
                      child: Text('Recommendation Details'),
                    ),
                  ),
                ),
                Expanded(
                  child: Card(
                    child: Center(
                      child: Text('Recommendation Charts'),
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
