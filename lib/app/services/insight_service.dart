import 'dart:convert';

import 'package:aws_connect_supervisor_app/app/models/insight.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:http/http.dart' as http;

class InsightService {
  int _currentBatchStartIndex = 0;
  List<Insight> _allInsights = [];

  // Fetch insights from the server
  Future<void> fetchInsights() async {
    final url = Uri.parse('http://localhost:8080/insights');

    try {
      final response = await http.get(url);

      if (response.statusCode == 200) {
        final data = json.decode(response.body) as Map<String, dynamic>;
        _allInsights = (data['insights'] as List<dynamic>)
            .map((json) => Insight.fromJson(json as Map<String, dynamic>))
            .toList();
      }
    } catch (error) {
      print(
          'Error fetching insights from server: $error. Defaulting to example json.');
      // Fallback to local JSON file
      await _loadInsightsFromLocal();
    }
  }

// Load insights from local JSON file
  Future<void> _loadInsightsFromLocal() async {
    try {
      final response = await rootBundle.loadString('assets/insights.json');
      final data = json.decode(response)
          as List<dynamic>; // Directly cast to List<dynamic>
      _allInsights = data
          .map((json) => Insight.fromJson(json as Map<String, dynamic>))
          .toList();
    } catch (error) {
      print('Error loading insights from local file: $error');
    }
  }

  // Load insights either from the server or a local file
  Future<List<Insight>> loadInsights() async {
    // Fetch from server or fallback to local JSON
    await fetchInsights();
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
