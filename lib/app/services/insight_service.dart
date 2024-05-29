import 'dart:convert';

import 'package:aws_connect_supervisor_app/app/models/insight.dart';
import 'package:flutter/services.dart';

class InsightService {
  int _currentBatchStartIndex = 0;
  List<Insight> _allInsights = [];

  Future<List<Insight>> loadInsights() async {
    final response = await rootBundle.loadString('assets/insights.json');
    final data = json.decode(response) as Map<String, dynamic>;
    _allInsights = (data['insights'] as List<dynamic>)
        .map((json) => Insight.fromJson(json as Map<String, dynamic>))
        .toList();
    return _getNextBatch();
  }

  List<Insight> refreshInsights() {
    return _getNextBatch();
  }

  List<Insight> _getNextBatch() {
    final batch = <Insight>[];
    for (var i = 0; i < 5; i++) {
      final index = (_currentBatchStartIndex + i) % _allInsights.length;
      batch.add(_allInsights[index]);
    }
    _currentBatchStartIndex =
        (_currentBatchStartIndex + 5) % _allInsights.length;
    return batch;
  }
}
