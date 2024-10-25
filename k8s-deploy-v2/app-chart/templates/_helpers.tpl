{{/* Generate basic labels for config-map*/}}
{{- define "config.labels" }}
  labels:
    date: {{ now | htmlDate }}
    version: {{ .Chart.Version }}
{{- end }}
