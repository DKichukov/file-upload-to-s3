

## S3 File Upload Service
A Spring Boot application that handles file uploads to AWS S3 buckets with size and type validation, along with features to view and delete uploaded images.

## Features
* File upload to AWS S3
* File validation (size and type)
* Supports JPEG and PNG formats
* Maximum file size: 1MB
* Simple and responsive UI
* View uploaded images
* Delete uploaded images

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

1. **Clone the repository:**
   ```bash
   git clone file-upload-to-s3
   ```

2. **Create a `.env` file in the project root:**
   ```makefile
   AWS_ACCESS_KEY_ID=your_aws_access_key
   AWS_SECRET_ACCESS_KEY=your_aws_secret_key
   AWS_S3_BUCKET_NAME=your_bucket_name
   ```

3. **Configure IDE Environment Variables:**
   #### IntelliJ IDEA
   * Go to `Run` → `Edit Configurations`
   * Select your Spring Boot configuration
   * Click on `'Modify options'` → `'Environment variables'`
   * Add the following variables (these are just examples, add your real variables):
     ```makefile
     AWS_ACCESS_KEY_ID=your_aws_access_key;AWS_SECRET_ACCESS_KEY=your_aws_secret_key;AWS_S3_BUCKET_NAME=your_bucket_name
     ```

### Build and Start the Containers

1. **Build and start the containers in detached mode.**
   This command will build the Docker image using the Dockerfile specified in the `docker-compose.yml` file.
   ```bash
   docker compose up -d
   ```

2. **Stop the Container:**
   ```bash
   docker compose down
   ```

## AWS S3 Setup

1. **Create an S3 bucket in your AWS account**
2. **Create an IAM user with the following permissions:**
   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Effect": "Allow",
         "Action": [
           "s3:PutObject",
           "s3:PutObjectAcl",
           "s3:GetObject",
           "s3:DeleteObject"
         ],
         "Resource": "arn:aws:s3:::your-bucket-name/*"
       }
     ]
   }
   ```
3. **Generate access key and secret key for the IAM user**
4. **Add these credentials to your environment variables**

## Usage

1. **Access the application at `http://localhost:8080/`**
2. **Select an image file (JPEG or PNG)**
3. **Click upload**
4. **Check the success/error message**

### Viewing Uploaded Images
- After uploading an image, you can view it by clicking on the "View Image" button next to the uploaded file.
- This will display the image in a new page or tab.

### Deleting Uploaded Images
- To delete an uploaded image, click on the "Delete Image" button next to the uploaded file.


## Build and Run
### Docker Build and Run

```bash
docker compose up --build -d
```

## Error Handling
The application handles the following error cases:
* Empty files
* Files exceeding size limit
* S3 upload failures

## Troubleshooting
### File Upload Errors
* Check file size (max 1MB)
* Verify file type (JPEG/PNG only)

