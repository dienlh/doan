'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('came_component', {
                parent: 'app',
                url: '/came_components',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Came_components'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/came_component/came_components.html',
                        controller: 'Came_componentController'
                    }
                },
                resolve: {
                }
            })
            .state('came_component.detail', {
                parent: 'app',
                url: '/came_component/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Came_component'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/came_component/came_component-detail.html',
                        controller: 'Came_componentDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Came_component', function($stateParams, Came_component) {
                        return Came_component.get({id : $stateParams.id});
                    }]
                }
            })
            .state('came_component.new', {
                parent: 'came_component',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/came_component/came_component-dialog.html',
                        controller: 'Came_componentDialogController',
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
                        $state.go('came_component', null, { reload: true });
                    }, function() {
                        $state.go('came_component');
                    })
                }]
            })
            .state('came_component.edit', {
                parent: 'came_component',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/came_component/came_component-dialog.html',
                        controller: 'Came_componentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Came_component', function(Came_component) {
                                return Came_component.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('came_component', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('came_component.delete', {
                parent: 'came_component',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/came_component/came_component-delete-dialog.html',
                        controller: 'Came_componentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Came_component', function(Came_component) {
                                return Came_component.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('came_component', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
