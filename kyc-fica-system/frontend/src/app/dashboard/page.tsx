'use client';

import React from 'react';
import Link from 'next/link';
import { Card } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';

export default function Dashboard() {
  const recentCases = [
    { id: 'KYC-2025-000123', customer: 'John Smith', type: 'KYC Verification', status: 'Completed', date: '2025-03-15' },
    { id: 'FICA-2025-000456', customer: 'Jane Doe', type: 'FICA Verification', status: 'In Progress', date: '2025-03-20' },
    { id: 'MORT-2025-000789', customer: 'Robert Johnson', type: 'Mortgage Verification', status: 'Pending', date: '2025-03-22' },
  ];

  const statusCounts = {
    pending: 12,
    inProgress: 8,
    completed: 24,
    rejected: 3,
  };

  return (
    <div className="space-y-8">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Dashboard</h1>
        <Button variant="primary">
          <Link href="/verification/new">New Verification</Link>
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <Card className="p-6 bg-blue-50 dark:bg-blue-900/20 border-l-4 border-blue-500">
          <h3 className="text-lg font-medium text-gray-700 dark:text-gray-300">Pending</h3>
          <p className="text-3xl font-bold mt-2">{statusCounts.pending}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Verification cases</p>
        </Card>
        
        <Card className="p-6 bg-yellow-50 dark:bg-yellow-900/20 border-l-4 border-yellow-500">
          <h3 className="text-lg font-medium text-gray-700 dark:text-gray-300">In Progress</h3>
          <p className="text-3xl font-bold mt-2">{statusCounts.inProgress}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Verification cases</p>
        </Card>
        
        <Card className="p-6 bg-green-50 dark:bg-green-900/20 border-l-4 border-green-500">
          <h3 className="text-lg font-medium text-gray-700 dark:text-gray-300">Completed</h3>
          <p className="text-3xl font-bold mt-2">{statusCounts.completed}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Verification cases</p>
        </Card>
        
        <Card className="p-6 bg-red-50 dark:bg-red-900/20 border-l-4 border-red-500">
          <h3 className="text-lg font-medium text-gray-700 dark:text-gray-300">Rejected</h3>
          <p className="text-3xl font-bold mt-2">{statusCounts.rejected}</p>
          <p className="text-sm text-gray-500 dark:text-gray-400 mt-1">Verification cases</p>
        </Card>
      </div>

      <div className="mt-8">
        <h2 className="text-xl font-semibold mb-4">Recent Verification Cases</h2>
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow overflow-hidden">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
              <thead className="bg-gray-50 dark:bg-gray-700">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Case ID</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Customer</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Type</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Status</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Created</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Actions</th>
                </tr>
              </thead>
              <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
                {recentCases.map((caseItem) => (
                  <tr key={caseItem.id}>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{caseItem.id}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{caseItem.customer}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{caseItem.type}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                        caseItem.status === 'Completed' ? 'bg-green-100 text-green-800' :
                        caseItem.status === 'In Progress' ? 'bg-yellow-100 text-yellow-800' :
                        'bg-gray-100 text-gray-800'
                      }`}>
                        {caseItem.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{caseItem.date}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <Link href={`/cases/${caseItem.id}`} className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300">View</Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
