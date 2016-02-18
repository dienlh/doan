'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_room', {
                parent: 'entity',
                url: '/status_rooms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_rooms'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_room/status_rooms.html',
                        controller: 'Status_roomController'
                    }
                },
                resolve: {
                }
            })
            .state('status_room.detail', {
                parent: 'entity',
                url: '/status_room/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_room'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_room/status_room-detail.html',
                        controller: 'Status_roomDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_room', function($stateParams, Status_room) {
                        return Status_room.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_room.new', {
                parent: 'status_room',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_room/status_room-dialog.html',
                        controller: 'Status_roomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_room', null, { reload: true });
                    }, function() {
                        $state.go('status_room');
                    })
                }]
            })
            .state('status_room.edit', {
                parent: 'status_room',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_room/status_room-dialog.html',
                        controller: 'Status_roomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_room', function(Status_room) {
                                return Status_room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_room.delete', {
                parent: 'status_room',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_room/status_room-delete-dialog.html',
                        controller: 'Status_roomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_room', function(Status_room) {
                                return Status_room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
