import Link from 'next/link';
import { Card } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';

export default function Home() {
  return (
    <div className="space-y-8">
      <section className="text-center py-12">
        <h1 className="text-4xl font-bold mb-4">KYC/FICA Verification System</h1>
        <p className="text-xl text-gray-600 dark:text-gray-300 max-w-3xl mx-auto">
          Enterprise-grade application for KYC and FICA verification during mortgage loan origination
        </p>
      </section>

      <section className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="text-center">
          <h2 className="text-2xl font-semibold mb-4">KYC Verification</h2>
          <p className="mb-6 text-gray-600 dark:text-gray-300">
            Verify customer identity and information for regulatory compliance
          </p>
          <Link href="/verification/kyc">
            <Button variant="primary" className="w-full">Start KYC Process</Button>
          </Link>
        </Card>

        <Card className="text-center">
          <h2 className="text-2xl font-semibold mb-4">FICA Verification</h2>
          <p className="mb-6 text-gray-600 dark:text-gray-300">
            Verify financial information and transactions for compliance
          </p>
          <Link href="/verification/fica">
            <Button variant="secondary" className="w-full">Start FICA Process</Button>
          </Link>
        </Card>

        <Card className="text-center">
          <h2 className="text-2xl font-semibold mb-4">Mortgage Verification</h2>
          <p className="mb-6 text-gray-600 dark:text-gray-300">
            Verify mortgage loan applications and property information
          </p>
          <Link href="/verification/mortgage">
            <Button variant="accent" className="w-full">Start Mortgage Process</Button>
          </Link>
        </Card>
      </section>

      <section className="mt-12">
        <h2 className="text-2xl font-semibold mb-6">Recent Verification Cases</h2>
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
                {/* Sample data - will be replaced with actual data from API */}
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">KYC-2025-000123</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">John Smith</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">KYC Verification</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">Completed</span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">2025-03-15</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <Link href="/cases/KYC-2025-000123" className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300">View</Link>
                  </td>
                </tr>
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">FICA-2025-000456</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">Jane Doe</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">FICA Verification</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">In Progress</span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">2025-03-20</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <Link href="/cases/FICA-2025-000456" className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300">View</Link>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>
    </div>
  );
}
