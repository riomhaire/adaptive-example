echo "Basic 1 client" `bombardier -c 1 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 2 client" `bombardier -c 2 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 3 client" `bombardier -c 3 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 4 client" `bombardier -c 4 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 5 client" `bombardier -c 5 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 6 client" `bombardier -c 6 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 7 client" `bombardier -c 7 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 8 client" `bombardier -c 8 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 9 client" `bombardier -c 9 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo "Basic 10 client" `bombardier -c 10 -d 60s 127.0.0.1:8080/helloBasic | grep Reqs`
sleep 10
echo ""
echo ""
echo "Adaptive 1 client" `bombardier -c 1 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 2 client" `bombardier -c 2 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 3 client" `bombardier -c 3 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 4 client" `bombardier -c 4 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 5 client" `bombardier -c 5 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 6 client" `bombardier -c 6 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 7 client" `bombardier -c 7 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 8 client" `bombardier -c 8 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 9 client" `bombardier -c 9 -d 60s 127.0.0.1:8080/hello | grep Reqs`
sleep 10
echo "Adaptive 10 client" `bombardier -c 10 -d 60s 127.0.0.1:8080/hello | grep Reqs`

