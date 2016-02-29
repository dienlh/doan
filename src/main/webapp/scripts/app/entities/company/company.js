'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('company', {
                parent: 'app',
                url: '/companys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Companys'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/company/companys.html',
                        controller: 'CompanyController'
                    }
                },
                resolve: {
                }
            })
            .state('company.detail', {
                parent: 'app',
                url: '/company/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Company'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/company/company-detail.html',
                        controller: 'CompanyDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Company', function($stateParams, Company) {
                        return Company.get({id : $stateParams.id});
                    }]
                }
            })
            .state('company.new', {
                parent: 'company',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    address: null,
                                    fax: null,
                                    phone_number: null,
                                    tax_code: null,
                                    bank_account: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('company');
                    })
                }]
            })
            .state('company.edit', {
                parent: 'company',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-dialog.html',
                        controller: 'CompanyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Company', function(Company) {
                                return Company.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('company.delete', {
                parent: 'company',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/company/company-delete-dialog.html',
                        controller: 'CompanyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Company', function(Company) {
                                return Company.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('company', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
