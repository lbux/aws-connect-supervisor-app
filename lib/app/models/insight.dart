class Insight {
  Insight({
    required this.id,
    required this.insightType,
    required this.value,
    required this.insight,
    required this.reason,
    required this.action,
    required this.metadata,
  });

  factory Insight.fromJson(Map<String, dynamic> json) {
    return Insight(
      id: json['id'] as int,
      insightType:
          InsightType.fromJson(json['insightType'] as Map<String, dynamic>),
      value: json['value'] as double,
      insight: json['insight'] as String,
      reason: json['reason'] as String,
      action: json['action'] as String,
      metadata: Map<String, dynamic>.from(json['metadata'] as Map),
    );
  }

  final int id;
  final InsightType insightType;
  final double value;
  final String insight;
  final String reason;
  final String action;
  final Map<String, dynamic> metadata;

  String get buttonLabel {
    return insightType.group;
  }
}

class InsightType {
  InsightType({
    required this.id,
    required this.group,
    required this.name,
  });

  factory InsightType.fromJson(Map<String, dynamic> json) {
    return InsightType(
      id: json['id'] as String,
      group: json['group'] as String,
      name: json['name'] as String,
    );
  }

  final String id;
  final String group;
  final String name;
}
