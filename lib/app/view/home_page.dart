import 'package:auto_size_text/auto_size_text.dart';
import 'package:aws_connect_supervisor_app/app/models/insight.dart';
import 'package:aws_connect_supervisor_app/app/services/insight_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int selectedIndex = 0;
  List<Insight> insights = [];
  final InsightService _insightService = InsightService();

  @override
  void initState() {
    super.initState();
    _loadInitialInsights();
  }

  Future<void> _loadInitialInsights() async {
    final loadedInsights = await _insightService.loadInsights();
    setState(() {
      insights = loadedInsights;
    });
  }

  void _refreshInsights() {
    setState(() {
      insights = _insightService.refreshInsights();
    });
  }

  void _showDetailSheet(BuildContext context) {
    showModalBottomSheet<void>(
      context: context,
      builder: (context) {
        final colorScheme = Theme.of(context).colorScheme;
        final selectedInsight = insights[selectedIndex];
        return ColoredBox(
          color: colorScheme.background,
          child: Column(
            children: [
              Expanded(
                flex: 3,
                child: InfoCard(
                  content: '''
${selectedInsight.reason}
\n${selectedInsight.action}''',
                  colorScheme: colorScheme,
                ),
              ),
              Expanded(
                child: InfoCard(
                  content: _formatMetadata(insights[selectedIndex].metadata),
                  colorScheme: colorScheme,
                ),
              ),
            ],
          ),
        );
      },
      isScrollControlled: true,
    );
  }

  String _formatMetadata(Map<String, dynamic> metadata) {
    final queueId = metadata['QueueId'] as String? ?? 'N/A';
    final agentId = metadata['AgentId'] as String?;
    final bedrockSources = (metadata['BedrockSources'] as List)
        .map((dynamic source) => source as Map<String, dynamic>)
        .toList();
    final sources = bedrockSources
        .map((Map<String, dynamic> source) => source['SourceName'] as String)
        .join(', ');

    final buffer = StringBuffer()..writeln('QueueID: $queueId');
    if (agentId != null) {
      buffer.writeln('AgentID: $agentId');
    }
    buffer.writeln('Sources: $sources');

    return buffer.toString();
  }

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;
    ScreenUtil.init(context);

    return Scaffold(
      backgroundColor: colorScheme.background,
      appBar: AppBar(
        title: const Text('Insights Board'),
        centerTitle: true,
        backgroundColor: colorScheme.secondary,
        actions: [
          Padding(
            padding: const EdgeInsets.all(8),
            child: IconButton(
              icon: const Icon(Icons.refresh),
              onPressed: _refreshInsights,
            ),
          ),
        ],
      ),
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (insights.isEmpty) {
            return const Center(child: CircularProgressIndicator());
          }
          if (constraints.maxWidth < 800) {
            return SingleChildScrollView(
              child: Column(
                children: List.generate(5, (index) {
                  return RecommendationCard(
                    index: index,
                    isSelected: selectedIndex == index,
                    onTap: () {
                      setState(() {
                        selectedIndex = index;
                      });
                      _showDetailSheet(context);
                    },
                    recommendation: insights[index].insight,
                    buttonLabel: insights[index].buttonLabel,
                    colorScheme: colorScheme,
                  );
                }),
              ),
            );
          } else {
            return Row(
              children: [
                Expanded(
                  child: SingleChildScrollView(
                    child: Column(
                      children: List.generate(5, (index) {
                        return RecommendationCard(
                          index: index,
                          isSelected: selectedIndex == index,
                          onTap: () {
                            setState(() {
                              selectedIndex = index;
                            });
                          },
                          recommendation: insights[index].insight,
                          buttonLabel: insights[index].buttonLabel,
                          colorScheme: colorScheme,
                        );
                      }),
                    ),
                  ),
                ),
                Expanded(
                  child: Column(
                    children: [
                      Expanded(
                        flex: 3,
                        child: InfoCard(
                          content: '''
${insights[selectedIndex].reason}
\n${insights[selectedIndex].action}''',
                          colorScheme: colorScheme,
                        ),
                      ),
                      Expanded(
                        child: InfoCard(
                          content:
                              _formatMetadata(insights[selectedIndex].metadata),
                          colorScheme: colorScheme,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            );
          }
        },
      ),
    );
  }
}

class RecommendationCard extends StatelessWidget {
  const RecommendationCard({
    required this.index,
    required this.isSelected,
    required this.onTap,
    required this.recommendation,
    required this.buttonLabel,
    required this.colorScheme,
    super.key,
  });

  final int index;
  final bool isSelected;
  final VoidCallback onTap;
  final String recommendation;
  final String buttonLabel;
  final ColorScheme colorScheme;

  @override
  Widget build(BuildContext context) {
    return Card(
      color: isSelected ? colorScheme.primaryContainer : colorScheme.background,
      child: InkWell(
        onTap: onTap,
        child: Padding(
          padding: const EdgeInsets.all(10),
          child: Column(
            children: [
              ListTile(
                title: AutoSizeText(
                  recommendation,
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 24,
                    color: isSelected ? Colors.white : Colors.black,
                  ),
                  minFontSize: 4,
                  maxLines: 3,
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              const SizedBox(height: 10),
              Align(
                alignment: Alignment.bottomRight,
                child: FilledButton.tonal(
                  onPressed: () {},
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all(
                      colorScheme.secondary,
                    ),
                  ),
                  child: Text(buttonLabel),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class InfoCard extends StatelessWidget {
  const InfoCard({
    required this.colorScheme,
    this.content = '',
    super.key,
  });

  final String content;
  final ColorScheme colorScheme;

  @override
  Widget build(BuildContext context) {
    return Card(
      color: colorScheme.primaryContainer,
      child: Center(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: AutoSizeText(
            content,
            textAlign: TextAlign.center,
            style: const TextStyle(fontSize: 40, color: Colors.white),
            minFontSize: 8,
            maxLines: 15,
            overflow: TextOverflow.ellipsis,
          ),
        ),
      ),
    );
  }
}
