# namespace.tf
resource "kubernetes_namespace" "challengeone" {
  metadata {
    name = "challengeone"
  }
}