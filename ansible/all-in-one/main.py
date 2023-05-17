import argparse
import yaml
import subprocess

def generate_instances(n):
    data = {
        'availability_zone': 'melbourne-qh2-uom',
        'instance_image': 'bbaf16f8-2484-48d7-b455-42209cc4b8d2',
        'instance_key_name': 'cloud',
        'instance_flavor': 'uom.mse.1c4g',
        'volumes': [],
        'security_groups': [
            {
                'name': 'ssh',
                'description': 'Security group for SSH access',
                'protocol': 'tcp',
                'port_range_min': 22,
                'port_range_max': 22,
                'remote_ip_prefix': '0.0.0.0/0'
            },
            {
                'name': 'http',
                'description': 'Security group for HTTP',
                'protocol': 'tcp',
                'port_range_min': 80,
                'port_range_max': 80,
                'remote_ip_prefix': '0.0.0.0/0'
            }
        ],
        'instances': []
    }

    for i in range(1, n + 1):
        instance_name = f'instance-{i}'
        volume_name = f'instance-{i}-vol'
        data['instances'].append({
            'name': instance_name,
            'volumes': [volume_name]
        })
        data['volumes'].append({
            'vol_name': volume_name,
            'vol_size': 50
        })

    return data

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-n', type=int, help='Number of instances to generate')
    args = parser.parse_args()

    if args.n is None or args.n <= 0:
        print('Please provide a positive number of instances and volumes.')
        return

    data = generate_instances(args.n)

    with open('./host_vars/nectar.yaml', 'w') as file:
        yaml.dump(data, file)
    
    subprocess.run(["./run-all-in-one.sh"], shell = True)

if __name__ == '__main__':
    main()
