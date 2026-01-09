# Exemple très simplifié (atelier)
# puppet apply manifests/site.pp

$user = 'ticketflow'
$group = 'ticketflow'
$app_name = 'ticketflow'
$web_root = '/var/www/ticketflow'

group { $group: ensure => present }

user { $user:
  ensure => present,
  gid    => $group,
  system => true,
}

package { ['nginx', 'openjdk-17-jre-headless']:
  ensure => installed,
}

file { $web_root:
  ensure => directory,
  owner  => 'www-data',
  group  => 'www-data',
  mode   => '0755',
}

service { 'nginx':
  ensure => running,
  enable => true,
}
