"use client";

import Link from "next/link";

interface BreadcrumbItem {
  label: string;
  href: string;
}

interface BreadcrumbProps {
  items: BreadcrumbItem[];
  currentPath: string;
}

export function Breadcrumb({ items, currentPath }: BreadcrumbProps) {
  return (
    <nav aria-label="breadcrumb">
      <ol className="flex space-x-2 text-gray-500">
        {items.map((item, index) => (
          <li key={index} className="flex items-center">
            <Link
              href={item.href}
              className={
                currentPath == item.href
                  ? "text-gray-600 hover:underline"
                  : "text-blue-600 hover:underline"
              }
            >
              {item.label}
            </Link>
            {index < items.length - 1 && (
              <span className="mx-2 text-gray-400">{">"}</span>
            )}
          </li>
        ))}
      </ol>
    </nav>
  );
}
