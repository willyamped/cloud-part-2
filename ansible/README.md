## How to Run
1. Export ssh private key
```
export PRIVATE_KEY="-----BEGIN RSA PRIVATE KEY-----
<paste the private key content here>
-----END RSA PRIVATE KEY-----"
```
2. Run the program
`python3 main.py -n [N]` where N is the number of mastodon harvesters to be created

## Prerequisites
1. `pip3.11 install ansible`
2. `pip3 install openstacksdk`
3. `pip3 install pyyaml`

## Versions
```
1. Ansible [Core 2.14.5]
2. Python 3.11.3
3. OpenStackSDK 1.0.0 (in openstack-common/main.yml)
4. OpenStack.Cloud 2.0.0 (in all-in-one.sh)
```

## Issues
1. If "Wait for connection" task fails, ensure that you are connected to VPN
