# deployment_app.tf
resource "kubernetes_deployment" "challengeone_app" {

  depends_on = [
    kubernetes_deployment.challengeone_db
  ]

  metadata {
    name      = "challengeone"
    namespace = kubernetes_namespace.challengeone.metadata[0].name
  }

  spec {

    selector {
      match_labels = {
        app = "challengeone"
      }
    }

    template {
      metadata {
        labels = {
          app = "challengeone"
        }
      }

      spec {
        container {
          name              = "challengeone"
          image             = "luigigb/challengeone-image:1.0"
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }

          env_from {
            secret_ref {
              name = kubernetes_secret.challengeone_db.metadata[0].name
            }
          }

          # liveness_probe {
          #   http_get {
          #     path = "/actuator/health/liveness" # ou /health se usar Spring Boot padrão
          #     port = 8080
          #   }
          #   initial_delay_seconds = 10
          #   period_seconds        = 10
          #   timeout_seconds       = 2
          #   failure_threshold     = 3
          # }

          # readiness_probe {
          #   http_get {
          #     path = "/actuator/health/readiness" # ou /health
          #     port = 8080
          #   }
          #   initial_delay_seconds = 5
          #   period_seconds        = 5
          #   timeout_seconds       = 2
          #   failure_threshold     = 3
          # }

          # =========================
          # Requests e Limits
          # =========================
          resources {
            requests = {
              cpu    = "200m"
              memory = "128Mi"
            }
          #   limits = {
          #     cpu    = "500m"
          #     memory = "256Mi"
          #   }
          }
        }
      }
    }
  }
}
