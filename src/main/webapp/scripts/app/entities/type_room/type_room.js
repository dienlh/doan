'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('type_room', {
                parent: 'app',
                url: '/type_rooms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Type_rooms'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/type_room/type_rooms.html',
                        controller: 'Type_roomController'
                    }
                },
                resolve: {
                }
            })
            .state('type_room.detail', {
                parent: 'app',
                url: '/type_room/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Type_room'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/type_room/type_room-detail.html',
                        controller: 'Type_roomDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Type_room', function($stateParams, Type_room) {
                        return Type_room.get({id : $stateParams.id});
                    }]
                }
            })
            .state('type_room.new', {
                parent: 'type_room',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_room/type_room-dialog.html',
                        controller: 'Type_roomDialogController',
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
                        $state.go('type_room', null, { reload: true });
                    }, function() {
                        $state.go('type_room');
                    })
                }]
            })
            .state('type_room.edit', {
                parent: 'type_room',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_room/type_room-dialog.html',
                        controller: 'Type_roomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Type_room', function(Type_room) {
                                return Type_room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('type_room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('type_room.delete', {
                parent: 'type_room',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/type_room/type_room-delete-dialog.html',
                        controller: 'Type_roomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Type_room', function(Type_room) {
                                return Type_room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('type_room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
