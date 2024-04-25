import 'package:auto_size_text/auto_size_text.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:flutter_svg/flutter_svg.dart';

List<String> recommendations = [
  'Agent in Sales queue is struggling with slow response times. Additional training recommended.',
  'Service level for Support queue is 5% below target. Consider taking action soon.',
  'Agents in Support queue are idle. Recommend moving to Sales queue.',
  'VIP queue experiencing an influx of calls. Recommend adding agents to the queue.',
  'Agent in Sales consistently receives bad customer feedback. Review past interactions.',
];

List<String> inDepthRecommendations = [
  'Agent Tom Harper in the Sales queue has an average response time of 30 seconds, significantly higher than the team average of 18 seconds. This slow response has been consistent over the past two weeks. To address this, a tailored training session focusing on efficient call handling and quick response techniques is recommended. This will likely help Tom decrease his response times to align with the team average.',
  'The current service level in the Support queue is at 75%, which is below our target of 80%. Analysis from the last 48 hours shows that the queue often struggles during the 10 AM to 12 PM window. To rectify this, consider scheduling additional agents during this peak period or reviewing current call handling procedures to enhance efficiency and meet the target service level.',
  'Data from the past week indicates that agents in the Support queue, such as Emily Johnson and Mark Lee, have a low contact rate with more than 50% idle time during their shifts. Meanwhile, the Sales queue has been experiencing a high volume of calls with a contacts-to-agent ratio of 3:1. Shifting Emily and Mark to the Sales queue could better utilize their capacity and balance the workload across queues.',
  'The VIP queue has seen a 40% increase in call volume this month, with average wait times increasing to 25 seconds per call. Adding agents, such as Sarah Connor from the General queue, where call volumes have decreased by 20%, to the VIP queue could help manage this influx effectively and maintain our standard of service excellence for VIP customers.',
  'Agent Jack Smith in the Sales queue has received consistently low customer satisfaction scores over his last 20 interactions, with particular complaints about resolution effectiveness and tone of communication. A detailed review of his past call recordings and customer feedback should be conducted to identify specific areas of improvement. Coaching or mentoring sessions should be scheduled based on the insights gained from the review.',
];

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int currentPageIndex = 0;
  int selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;
    ScreenUtil.init(context);

    return Scaffold(
      backgroundColor: colorScheme.background,
      appBar: AppBar(
        title: const Text('Home Page'),
        centerTitle: true,
        backgroundColor: colorScheme.secondary,
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
            child: Column(
              children: List.generate(5, (index) {
                final buttonText = (index.isEven) ? 'Agent' : 'Queue';
                return Expanded(
                  child: Card(
                    color: selectedIndex == index
                        ? colorScheme.primaryContainer
                        : colorScheme.background,
                    child: InkWell(
                      onTap: () {
                        setState(() {
                          selectedIndex = index;
                        });
                      },
                      child: Padding(
                        padding: const EdgeInsets.all(10),
                        child: Column(
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            ListTile(
                              title: AutoSizeText(
                                recommendations[index],
                                textAlign: TextAlign.center,
                                style: const TextStyle(fontSize: 24),
                                minFontSize: 4,
                                maxLines: 3,
                                overflow: TextOverflow.ellipsis,
                              ),
                            ),
                            const Spacer(),
                            Align(
                              alignment: Alignment.bottomRight,
                              child: FilledButton(
                                onPressed: () {},
                                style: ButtonStyle(
                                  backgroundColor: MaterialStateProperty.all(
                                    colorScheme.secondaryContainer,
                                  ),
                                ),
                                child: Text(buttonText),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                );
              }),
            ),
          ),
          Expanded(
            child: Column(
              children: [
                Expanded(
                  child: Card(
                    color: colorScheme.primaryContainer,
                    child: Center(
                      child: Padding(
                        padding: const EdgeInsets.only(left: 16, right: 16),
                        child: AutoSizeText(
                          textAlign: TextAlign.center,
                          inDepthRecommendations[selectedIndex],
                          style: const TextStyle(fontSize: 30),
                          minFontSize: 8,
                          maxLines: 8,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Card(
                    color: colorScheme.primaryContainer,
                    child: Center(
                      child: SvgPicture.asset('temp.svg'),
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
