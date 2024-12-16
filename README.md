# S3 File Upload Service
A Spring Boot application that handles file uploads to AWS S3 buckets with size and type validation.

## Features
* File upload to AWS S3
* File validation (size and type)
* Supports JPEG and PNG formats
* Maximum file size: 1MB
* Simple and responsive UI

## Prerequisites
* Java 17 or higher
* Maven
* Docker and Docker Compose
* AWS Account with S3 access

## Environment Variables
The application requires the following environment variables:
```makefile
AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
AWS_S3_BUCKET_NAME=your_bucket_name
```

## Setup Instructions
### Local Development Setup

1. Clone the repository:
   ```bash
git clone file-upload-to-s3
```
2. Create a `.env` file in the project root:
   ```makefile
AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
AWS_S3_BUCKET_NAME=your_bucket_name
```
3. Configure IDE Environment Variables:
   #### IntelliJ IDEA
    * Go to `Run` → `Edit Configurations`
    * Select your Spring Boot configuration
    * Click on `'Modify options'` → `'Environment variables'`
    * Add the following variables (these are just examples, add your real variables):
      ```makefile
AWS_ACCESS_KEY_ID=your_aws_access_key;AWS_SECRET_ACCESS_KEY=your_aws_secret_key;AWS_S3_BUCKET_NAME=your_bucket_name
```

### Build and Start the Containers

1. Build and start the containers in detached mode.
   This command will build the Docker image using the Dockerfile specified in the `docker-compose.yml` file.
   ```bash
docker compose up -d
```
2. Stop the Container:
   ```bash
docker compose down
```

## AWS S3 Setup

1. Create an S3 bucket in your AWS account
2. Create an IAM user with the following permissions:
   ```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:PutObject",
        "s3:PutObjectAcl"
      ],
      "Resource": "arn:aws:s3:::your-bucket-name/*"
    }
  ]
}
```
3. Generate access key and secret key for the IAM user
4. Add these credentials to your environment variables

## Usage

1. Access the application at `http://localhost:8080/file-upload`
2. Select an image file (JPEG or PNG)
3. Click upload
4. Check the success/error message

## Build and Run
### Docker Build and Run

# Build and run image
```bash
docker compose up --build -d
```

## Error Handling
The application handles the following error cases:
* Empty files
* Invalid file types
* Files exceeding size limit
* S3 upload failures

## Troubleshooting
### AWS Credentials Error
* Verify environment variables are set correctly
* Check IAM user permissions
* Ensure bucket name is correct

### File Upload Errors
* Check file size (max 1MB)
* Verify file type (JPEG/PNG only)
* Check application logs for detailed error messages

### Docker Issues
* Ensure Docker daemon is running
* Verify environment variables in `.env` file
* Check Docker logs: `docker logs s3`
