#!/bin/bash

# Build and Run Script for Eskimi Backend Assignment
# This script provides an easy way to build and run the application

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
IMAGE_NAME="eskimi-backend-assignment"
CONTAINER_NAME="eskimi-backend"
PORT=8080

print_header() {
    echo -e "${BLUE}=====================================${NC}"
    echo -e "${BLUE}  Eskimi Backend Assignment${NC}"
    echo -e "${BLUE}=====================================${NC}"
    echo ""
}

print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}→ $1${NC}"
}

check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed. Please install Docker first."
        echo "Visit: https://docs.docker.com/get-docker/"
        exit 1
    fi
    print_success "Docker is installed"
}

stop_existing_container() {
    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
        print_info "Stopping existing container..."
        docker stop $CONTAINER_NAME 2>/dev/null || true
        docker rm $CONTAINER_NAME 2>/dev/null || true
        print_success "Existing container removed"
    fi
}

build_image() {
    print_info "Building Docker image (this may take a few minutes)..."
    print_info "Running tests as part of the build process..."

    if docker build -t $IMAGE_NAME:latest .; then
        print_success "Docker image built successfully"
        print_success "All tests passed"
    else
        print_error "Build failed"
        exit 1
    fi
}

run_container() {
    print_info "Starting container..."

    docker run -d \
        --name $CONTAINER_NAME \
        -p $PORT:8080 \
        $IMAGE_NAME:latest

    print_success "Container started"
}

wait_for_app() {
    print_info "Waiting for application to start..."

    for i in {1..30}; do
        if curl -s http://localhost:$PORT/actuator/health > /dev/null 2>&1; then
            print_success "Application is ready!"
            return 0
        fi
        sleep 2
        echo -n "."
    done

    echo ""
    print_error "Application failed to start within 60 seconds"
    print_info "Check logs with: docker logs $CONTAINER_NAME"
    exit 1
}

show_usage() {
    echo -e "${GREEN}Application is running!${NC}"
    echo ""
    echo "API Base URL: http://localhost:$PORT"
    echo ""
    echo "Available endpoints:"
    echo "  - POST /api/v1/dates/difference"
    echo "  - POST /api/v1/number/number-to-words"
    echo "  - POST /api/v1/weather/dhaka-stats"
    echo "  - GET  /actuator/health"
    echo ""
    echo "Useful commands:"
    echo "  View logs:        docker logs -f $CONTAINER_NAME"
    echo "  Stop application: docker stop $CONTAINER_NAME"
    echo "  Start again:      docker start $CONTAINER_NAME"
    echo "  Remove container: docker rm -f $CONTAINER_NAME"
    echo ""
    echo "Test the API:"
    echo "  curl http://localhost:$PORT/actuator/health"
    echo ""
}

show_example() {
    echo -e "${YELLOW}Example API calls:${NC}"
    echo ""
    echo "1. Calculate days between dates:"
    echo "   curl -X POST http://localhost:$PORT/api/v1/dates/difference \\"
    echo "     -H 'Content-Type: application/json' \\"
    echo "     -d '{\"startDate\":\"2024-01-01\",\"endDate\":\"2024-12-31\"}'"
    echo ""
    echo "2. Convert number to words:"
    echo "   curl -X POST http://localhost:$PORT/api/v1/number/number-to-words \\"
    echo "     -H 'Content-Type: application/json' \\"
    echo "     -d '{\"number\":36.40}'"
    echo ""
    echo "3. Get temperature stats:"
    echo "   curl -X POST http://localhost:$PORT/api/v1/weather/dhaka-stats \\"
    echo "     -H 'Content-Type: application/json' \\"
    echo "     -d '{\"startDate\":\"2026-01-01\",\"endDate\":\"2026-01-10\"}'"
    echo ""
}

# Main execution
main() {
    print_header

    check_docker
    stop_existing_container
    build_image
    run_container
    wait_for_app

    echo ""
    show_usage
    show_example
}

# Run main function
main