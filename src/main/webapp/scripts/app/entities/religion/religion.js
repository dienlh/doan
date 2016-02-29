'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('religion', {
                parent: 'app',
                url: '/religions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Religions'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/religion/religions.html',
                        controller: 'ReligionController'
                    }
                },
                resolve: {
                }
            })
            .state('religion.detail', {
                parent: 'app',
                url: '/religion/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Religion'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/religion/religion-detail.html',
                        controller: 'ReligionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Religion', function($stateParams, Religion) {
                        return Religion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('religion.new', {
                parent: 'religion',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/religion/religion-dialog.html',
                        controller: 'ReligionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('religion', null, { reload: true });
                    }, function() {
                        $state.go('religion');
                    })
                }]
            })
            .state('religion.edit', {
                parent: 'religion',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/religion/religion-dialog.html',
                        controller: 'ReligionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Religion', function(Religion) {
                                return Religion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('religion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('religion.delete', {
                parent: 'religion',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/religion/religion-delete-dialog.html',
                        controller: 'ReligionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Religion', function(Religion) {
                                return Religion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('religion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
