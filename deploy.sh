echo "🌀🌀🌀Deploying Recon Backend  🌀🌀🌀"
echo "🌀🌀🌀Creating container and pushing it to GCP registry 🌀🌀🌀"

# shellcheck disable=SC2283
IMAGE="tigerbackend"
echo
echo "🌀🌀🌀 IMAGE:" $IMAGE

gcloud projects add-iam-policy-binding recon-back \
    --member=serviceAccount:734454946254-compute@developer.gserviceaccount.com \
    --role=roles/cloudbuild.builds.builder

./mvnw compile com.google.cloud.tools:jib-maven-plugin:3.3.1:build \
  -Dimage=gcr.io/recon-back/$IMAGE
echo
echo "🍎🍎🍎Deploy newly created Recon Backend container to Cloud Run 🍎🍎🍎"
echo
gcloud run deploy $IMAGE \
     --region=europe-west2 \
     --platform=managed \
     --project=recon-back \
     --allow-unauthenticated \
     --image=gcr.io/recon-back/$IMAGE
echo
echo "🍎🍎🍎 Deployed Recon Backend on Cloud Run 🍎🍎🍎"
echo

