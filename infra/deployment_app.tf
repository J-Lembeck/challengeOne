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
    replicas = 2

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
        }
      }
    }
  }
}
