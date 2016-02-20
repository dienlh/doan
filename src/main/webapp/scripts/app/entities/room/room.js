'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('room', {
                parent: 'entity',
                url: '/rooms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Rooms'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/room/rooms.html',
                        controller: 'RoomController'
                    }
                },
                resolve: {
                }
            })
            .state('room.detail', {
                parent: 'entity',
                url: '/room/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Room'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/room/room-detail.html',
                        controller: 'RoomDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Room', function($stateParams, Room) {
                        return Room.get({id : $stateParams.id});
                    }]
                }
            })
            .state('room.new', {
                parent: 'room',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-dialog.html',
                        controller: 'RoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    key_code: null,
                                    title: null,
                                    isPet: false,
                                    isBedKid: false,
                                    number_of_livingroom: null,
                                    number_of_bedroom: null,
                                    number_of_toilet: null,
                                    number_of_kitchen: null,
                                    number_of_bathroom: null,
                                    floor: null,
                                    orientation: null,
                                    surface_size: null,
                                    maxAdults: null,
                                    maxKids: null,
                                    daily_price: null,
                                    monthly_price: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('room');
                    })
                }]
            })
            .state('room.edit', {
                parent: 'room',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-dialog.html',
                        controller: 'RoomDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Room', function(Room) {
                                return Room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('room.delete', {
                parent: 'room',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/room/room-delete-dialog.html',
                        controller: 'RoomDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Room', function(Room) {
                                return Room.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('room', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
